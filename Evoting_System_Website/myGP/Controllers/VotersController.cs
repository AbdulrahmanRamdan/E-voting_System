using System;
using System.Web.Mvc;
using Google.Cloud.Firestore;

namespace myGP.Controllers
{
    public class VotersController : Controller
    {
        FirestoreDb db;
        public VotersController()
        {
            string path = AppDomain.CurrentDomain.BaseDirectory + "asp-mvc-with-web.json";
            Environment.SetEnvironmentVariable("GOOGLE_APPLICATION_CREDENTIALS", path);
            db = FirestoreDb.Create("asp-mvc-with-web");
        }
        public ActionResult Index()
        {
            return Content("Done");
        }
    }
}