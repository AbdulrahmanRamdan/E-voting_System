using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Google.Cloud.Firestore;
using System.Drawing;
namespace myGP.Models
{
    [FirestoreData]
    public class Candidate
    {
        [FirestoreProperty]
        public string Fb_id { get; set; }
        [FirestoreProperty]
        public string National_id { get; set; }
        [FirestoreProperty]
        public string Name { get; set; }
        [FirestoreProperty]
        public string Email { get; set; }
        [FirestoreProperty]
        public string Phone { get; set; }
        [FirestoreProperty]
        public string party { get; set; }
        [FirestoreProperty]
        public string slogan { get; set; }
        [FirestoreProperty]
        public string program { get; set; }
        [FirestoreProperty]
        public int Number_of_votes { get; set; }
        [FirestoreProperty]
        public string link_requied_paper { get; set; }
        [FirestoreProperty]
        public Bitmap image { get; set; }
        [FirestoreProperty]
        public Bitmap Slogan_image { get; set; }
    }    
}