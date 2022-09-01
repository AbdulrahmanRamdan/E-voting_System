using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Web;
using System.Web.Mvc;
using Google.Cloud.Firestore;
using myGP.Models;
using myGP.Models_Controllers;
namespace myGP.Controllers
{
    public class voterController : Controller
    {
        // GET: voter
        VoterController vv;
        FirestoreDb db;
        public voterController()
        {
            string path = AppDomain.CurrentDomain.BaseDirectory + "asp-mvc-with-web.json";
            Environment.SetEnvironmentVariable("GOOGLE_APPLICATION_CREDENTIALS", path);
            db = FirestoreDb.Create("asp-mvc-with-web");
            vv = new VoterController();
        }
        public ActionResult loginByNationalID()  
        {
            return View();
        }
        [HttpPost]
        public async Task<ActionResult> loginByNationalID(string s) 
        {
            if (await vv.Check_For_National_id(s)){
                Voter voter = await vv.Get_Voter_Data(s);
                TempData["name"] = voter.Name;
                TempData["national_id"] = voter.National_id;
                string addr = voter.Address.city + "," + voter.Address.area;
                TempData["Address"] = addr;
                TempData["job"] = voter.Job;
                TempData["Gender"] = voter.Gender;
                TempData["Religion"] = voter.Religion;
                TempData["Marital_status"] = voter.Marital_status;
                TempData["RightToVote"] = voter.RightToVote;
                TempData["Status_Of_Voting"] = voter.Status_Of_Voting;
                string[] arr = { voter.Name, voter.National_id, addr, voter.Job, voter.Gender,
                             voter.Religion, voter.Marital_status,
                             voter.RightToVote,voter.Status_Of_Voting};
                return RedirectToAction("voterInfo", "voter", arr);
            }else
                return HttpNotFound();
        }
        public ActionResult voterInfo()
        {
            return View("voterInfo");
        }
        
    }
}