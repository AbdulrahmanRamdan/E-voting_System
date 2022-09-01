using System.Collections.Generic;
using System.Web.Mvc;
using myGP.Models;
using System.Threading.Tasks;
using myGP.Models_Controllers;
using System.IO;
using Firebase.Auth;
using System.Threading;
using Firebase.Storage;
using System;
using System.Web;
using Google.Cloud.Firestore;

namespace myGP.Controllers
{
    public class candidateController : Controller
    {
        VoterController vv;
        FirestoreDb db;
        private static string AuthEmail = "usamamohamed306@gmail.com";
        private static string AuthPass = "Ma-Na011";
        private static string ApiKey = "AIzaSyD_A0esVzbW69uR-R6copAzWrSEh1Il6ic";
        private static string Bucket = "asp-mvc-with-web.appspot.com";

        public candidateController()
        {
            string path = AppDomain.CurrentDomain.BaseDirectory + "asp-mvc-with-web.json";
            Environment.SetEnvironmentVariable("GOOGLE_APPLICATION_CREDENTIALS", path);
            db = FirestoreDb.Create("asp-mvc-with-web");
            vv = new VoterController();
        }
        public ActionResult applyForPresidency()
        {
            return View();
        }
        // GET: candidate
        [HttpPost]
        public async Task<ActionResult> applyForPresidency(HttpPostedFileBase file,string national_id,string phone,string email ,string link_paper)
        {
            FileStream fs;
            if (file.ContentLength > 0)
            {
                string path = Path.Combine(Server.MapPath("~/App_Data/files"), file.FileName);
                file.SaveAs(path);
                fs = new FileStream(Path.Combine(path), FileMode.Open);
                await Task.Run(() => uploadimage(fs, file.FileName, national_id, phone, email, link_paper));
            }
            return View("applyForPresidency");
        }

        public async Task<string> uploadimage(FileStream fs, string fname, string national_id, string phone,string email,string papers)
        {
            Request_Candidates req = new Request_Candidates();
            string link = null;
            var auth = new FirebaseAuthProvider(new FirebaseConfig(ApiKey));
            var a = await auth.SignInWithEmailAndPasswordAsync(AuthEmail, AuthPass);
            var cancellation = new CancellationTokenSource();
            var task = new FirebaseStorage(
                Bucket,
                new FirebaseStorageOptions
                {
                    AuthTokenAsyncFactory = () => Task.FromResult(a.FirebaseToken),
                    ThrowOnCancel = true
                })
                .Child("images")
                .Child(fname)
                .PutAsync(fs, cancellation.Token);
            try
            {
                link = await task;
                req.National_id = national_id;
                req.image_link = link;
                req.Phone = phone;
                req.Email = email;
                req.link_requied_paper =papers;
                vv.Add_Candidate_Request(req);

            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception was thrown: {0}", ex);
            }
            return link;
        }

        public async Task<ActionResult> candidatePrograms()
        {
            List<Official_Candidates> lst = await vv.Retrieve_Official_Candidates();   
            ViewData["lst"] = lst;
            return View(lst);
        }
        public ActionResult searchForCandidate()
        {
            return View();
        }

        [HttpPost]
        public async Task<ActionResult> searchForCandidate(string candidateName)
        {
            if (await vv.SearchForSpecificCandidate(candidateName))
            {
                List<Official_Candidates> c = await vv.Get_Candidate(candidateName);
                TempData["list"] = c;
                TempData.Keep();
                return RedirectToAction("candidateInfo", "candidate", c);
            }else
            {
                return HttpNotFound();
            }
        }

        public ActionResult candidateInfo(List<Official_Candidates>lst)
        {
            return View(TempData["list"]);
        }
       
    }
}