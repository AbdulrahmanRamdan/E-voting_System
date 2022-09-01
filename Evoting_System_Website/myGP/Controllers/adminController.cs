using System;
using System.Collections.Generic;
using System.Web;
using System.Web.Mvc;
using Google.Cloud.Firestore;
using myGP.Models_Controllers;
using myGP.Models;
using System.Threading.Tasks;
using System.IO;
using System.Threading;
using Firebase.Auth;
using Firebase.Storage;

namespace myGP.Controllers
{
    public class adminController : Controller
    {
        VoterController vv;
        FirestoreDb db;

        private static string AuthEmail = "usamamohamed306@gmail.com";
        private static string AuthPass = "Ma-Na011";
        private static string ApiKey = "AIzaSyD_A0esVzbW69uR-R6copAzWrSEh1Il6ic";
        private static string Bucket = "asp-mvc-with-web.appspot.com";
        public adminController()
        {
            string path = AppDomain.CurrentDomain.BaseDirectory + "asp-mvc-with-web.json";
            Environment.SetEnvironmentVariable("GOOGLE_APPLICATION_CREDENTIALS", path);
            db = FirestoreDb.Create("asp-mvc-with-web");
            vv = new VoterController();
        }
        // GET: admin
        public ActionResult adminLogin()
        {
            return View();
        }
        [HttpPost]
        public async Task<ActionResult> adminLogin(string user,string pass)
        {
            if (await vv.Check_For_Admin_Data(user, pass)){
                Admin ad = await vv.Get_Admin_data(user, pass);
                return RedirectToAction("homeAdmin", "admin");
            }
            else
                return HttpNotFound();
        }
        public ActionResult homeAdmin()
        {
            return View("homeAdmin");
        }
        public async Task<ActionResult> manageDisabled()
        {
            List<Disabled_People> req = await vv.Retrieve_Disabled_Requests();
            ViewData["MyData"] = req;
            return View(req);
        }
       // [HttpPost]
        [Route("admin/candidateRequestDetails/{id}")]
        public async Task<ActionResult> candidateRequestDetails(string id, string method=null)
        {
            if (String.IsNullOrEmpty(method))
            {
                Request_Candidates req = await vv.Candidate_Request(id);
                TempData["x"] = id;
                TempData.Keep();
                return View(req);
            }
            else
            {
                await vv.CheckStatusForRequestCandidate(method, id);
                return RedirectToAction("manageCandidate", "admin");
            }
        }
        public async Task<ActionResult> manageCandidate()
        {
            List<Request_Candidates> req= await vv.Retrieve_Candidates_Request();
            ViewData["MyData"] = req;
            return View(req);
        }

        


        public async Task<ActionResult> manageMachine() 
        {
            List<Machine> machines = await vv.Retrieve_Machines();
            return View(machines);
        }

        public ActionResult addNewCandidate()
        {
            return View();
        }
        [HttpPost]
        public async Task<ActionResult> addNewCandidate(HttpPostedFileBase file, string candidateName,string candidatePapers)
        {
            FileStream fs;
            if (file.ContentLength > 0)
            {
                 string path = Path.Combine(Server.MapPath("~/App_Data/files"), file.FileName);
                 file.SaveAs(path);
                 fs = new FileStream(Path.Combine(path), FileMode.Open);
                 await Task.Run(() => uploadimage(fs, file.FileName, candidateName, candidatePapers));
            }
            return View();
        }
        public async Task<string> uploadimage(FileStream fs, string fname, string candName,string pdflink)
        {
            Official_Candidates new_candidate = new Official_Candidates();
            new_candidate.candidate_name = candName;
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
                new_candidate.image_link = link;
                //PDF_Link here
                new_candidate.pdf_link = pdflink;
                new_candidate.Num_Of_Votes = 0;
                vv.Add_Official_Candidates(new_candidate);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception was thrown: {0}", ex);
            }
            return link;
        }
    }
}