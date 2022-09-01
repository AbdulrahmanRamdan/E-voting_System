using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using myGP.Models;
using System.Threading.Tasks;
using myGP.Models_Controllers;

namespace myGP.Controllers
{
    public class electionProcessController : Controller
    {
        VoterController vv = new VoterController();
        // GET: electionProcess
        public async Task<ActionResult> followUp()
        {
            List<Official_Candidates> lst = await vv.Retrieve_Official_Candidates_Ranked();
            ViewData["lst"] = lst;
            return View(lst);
        }
    }
}