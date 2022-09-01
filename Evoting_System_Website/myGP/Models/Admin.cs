using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Google.Cloud.Firestore;

namespace myGP.Models
{
    [FirestoreData]
    public class Admin
    {
        [FirestoreProperty]
        public string Fb_id { get; set; }
        [FirestoreProperty]
        public string User_name { get; set; }
        [FirestoreProperty]
        public string National_id { get; set; }
        [FirestoreProperty]
        public string password { get; set; }
    }
}