using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Google.Cloud.Firestore;

namespace myGP.Models
{
    [FirestoreData]
    public class Machine
    {

        [FirestoreProperty]
        public string Fb_id { get; set; }
        [FirestoreProperty]
        public string id { get; set; }
        [FirestoreProperty]
        public string Model { get; set; }
        [FirestoreProperty]
        public Address address { get; set; }
        [FirestoreProperty]
        public string FB_id { get; set; }
        [FirestoreProperty]
        public string location { get; set; }
        [FirestoreProperty]
        public string Working_statues { get; set; }
    }
}