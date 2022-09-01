using Google.Cloud.Firestore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace myGP.Models
{
    public class Request_Candidates
    {
        [FirestoreProperty]
        public string Fb_id { get; set; }
        [FirestoreProperty]
        public string National_id { get; set; }
     
        [FirestoreProperty]
        public string Email { get; set; }
        [FirestoreProperty]
        public string Phone { get; set; }
        [FirestoreProperty]
        public string link_requied_paper { get; set; }
        [FirestoreProperty]
        public string image_link { get; set; }

    }
}