using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Web;
using Google.Cloud.Firestore;
namespace myGP.Models
{
    [FirestoreData]
    public class Voter
    {
        [FirestoreProperty]
        public string Fb_id { get; set; }
        [FirestoreProperty]
        public string National_id { get; set; }
        [FirestoreProperty]
        public string Name { get; set; }
        [FirestoreProperty]
        public Address Address { get; set; }
        [FirestoreProperty]
        public string Marital_status { get; set; }
        [FirestoreProperty]
        public string Job { get; set; }
        [FirestoreProperty]
        public DateTime BirthDate { get; set; }
        [FirestoreProperty]
        public int Age { get; set; }
        [FirestoreProperty]
        public string Religion { get; set; }
        [FirestoreProperty]
        public string Phone_Number { get; set; }
        [FirestoreProperty]
        public string Qualifications { get; set; }
        [FirestoreProperty]
        public string RightToVote { get; set; }
        [FirestoreProperty]
        public string Gender { get; set; }
        [FirestoreProperty]
        public string Status_Of_Voting { get; set; }





    }
}