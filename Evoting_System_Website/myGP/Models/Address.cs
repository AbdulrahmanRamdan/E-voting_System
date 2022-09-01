using Google.Cloud.Firestore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace myGP.Models
{
    [FirestoreData]
    public class Address
    {
        [FirestoreProperty]
        public int house_number { get; set; }
        [FirestoreProperty]
        public string streert_name { get; set; }
        [FirestoreProperty]
        public string area   { get; set; }
        [FirestoreProperty]
        public string city { get; set; }
    }
}