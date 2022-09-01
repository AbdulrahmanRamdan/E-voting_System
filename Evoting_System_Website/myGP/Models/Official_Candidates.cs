using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Google.Cloud.Firestore;

namespace myGP.Models
{
     [FirestoreData]
    public class Official_Candidates
    {
        [FirestoreProperty]
        public string Fb_id { get; set; }
        [FirestoreProperty]
        public string candidate_name { get; set; }
        [FirestoreProperty]
        public string image_link { get; set; }
        [FirestoreProperty]
        public string pdf_link { get; set; }
        [FirestoreProperty]
        public int Num_Of_Votes { get; set; }

    }
}