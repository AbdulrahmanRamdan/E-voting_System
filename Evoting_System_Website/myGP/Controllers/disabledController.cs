using System.Web.Mvc;
using myGP.Models_Controllers;
using myGP.Models;
namespace myGP.Controllers
{
    public class disabledController : Controller
    {

        VoterController vv = new VoterController();
        // GET: disabled
        [HttpPost]
        public ActionResult serviceFor_disabled(string national_id,string phone1,string city1,string link_paper1)
        {
            Disabled_People dis = new Disabled_People();
            dis.National_id = national_id;
            dis.Phone = phone1;
            dis.city = city1;
            dis.link = link_paper1;
            vv.Add_Disable_Request(dis);
            return View("serviceFor_disabled");
        }
        public ActionResult serviceFor_disabled()
        {
            return View();
        }
    }
}