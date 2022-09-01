using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Google.Cloud.Firestore;
namespace myGP.Models
{
    [FirestoreData]
    public class Vote
    {
        [FirestoreProperty]
        public string FB_id { get; set; }
        [FirestoreProperty]
        public string voter_n_id { get; set; }
        [FirestoreProperty]
        public string Machine_id { get; set; }
        [FirestoreProperty]
        public string candidate_n_id { get; set; }
        public DateTime date_of_voting { get; set; }
        public string Statues { get; set; }
    }
}