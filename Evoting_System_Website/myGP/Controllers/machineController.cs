using System.Threading.Tasks;
using System.Web.Mvc;
using myGP.Models_Controllers;

namespace myGP.Controllers
{
    public class machineController : Controller
    {
        public ActionResult enterAddress()
        {
            return View();
        }
        // GET: machine
        [ActionName("enterAddress")]
        [HttpPost]
        
        public async Task<ActionResult> enterAddress(string cityVal, string areaVal, string streetVal)
        { 
            VoterController vv = new VoterController();
            string s =await vv.GetNearestMachine(cityVal, areaVal,streetVal);
            Session["newAddress"] = s;
            return RedirectToAction("getAddress","machine",s);
        }
        public ActionResult getAddress(string addr)
        {
            string ad = addr;
            Session["machine_address"] = Session["newAddress"];
            return View();
        }
    }
}