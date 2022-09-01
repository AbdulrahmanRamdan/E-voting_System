using System;
using System.Collections.Generic;
using Google.Cloud.Firestore;
using System.Threading.Tasks;
using myGP.Models;

namespace myGP.Models_Controllers
{
    public class VoterController
    {
        public static int counter = 0;
        FirestoreDb db;
        public VoterController()
        {
            string path = AppDomain.CurrentDomain.BaseDirectory + "asp-mvc-with-web.json";
            Environment.SetEnvironmentVariable("GOOGLE_APPLICATION_CREDENTIALS", path);
            db = FirestoreDb.Create("asp-mvc-with-web");
        }
        public async Task<string> Allow_To_Vote(string National_id)
        {
            string allow_vote = "يحق له";
            string not_allow_to_vote = "لا يحق له";
            Query Q = db.Collection("Voters").WhereEqualTo("National_ID", National_id);
            QuerySnapshot snap = await Q.GetSnapshotAsync();
            foreach (DocumentSnapshot docsnap in snap)
            {
                Voter voter = new Voter();
                if (docsnap.Exists)
                {
                    voter.Age = int.Parse(docsnap.GetValue<string>("Age"));
                    if (voter.Age > 18) return allow_vote;
                    else return not_allow_to_vote;
                }
            }
            return null;
        }
        public async Task<bool> AreVotingOrNot(string National_id)
        {
            Query Q = db.Collection("Vote_Records");
            QuerySnapshot snap = await Q.GetSnapshotAsync();
            foreach (DocumentSnapshot docsnap in snap)
            {
                Vote vt = new Vote();
                if (docsnap.Exists)
                {
                    vt.voter_n_id = docsnap.GetValue<string>("voter_n_id");
                    if (vt.voter_n_id.Equals(National_id)) return true;
                    else return false;
                }
            }
            return false;
        }
        public async Task<bool> SearchForSpecificCandidate(string Candidate_Name)
        {
            Query Q = db.Collection("Official Candidates").WhereEqualTo("Name",Candidate_Name);
            QuerySnapshot snap = await Q.GetSnapshotAsync();
            foreach (DocumentSnapshot docsnap in snap)
            {
                if (docsnap.Exists)
                {
                    Candidate c = new Candidate();
                    c.Name = docsnap.GetValue<string>("Name");
                    if (c.Name.Equals(Candidate_Name)) return true;
                    else return false;
                }
            }
            return false;
        }
        /*
        public async Task<bool> CheckForStatusMachine(string id)
        {
            Machine m = new Machine();
            string _worked = "Worked";
            string _unworked = "unworked";
            Query Q = db.Collection("Machines").WhereEqualTo("Id",id);
            QuerySnapshot snap2 = await Q.GetSnapshotAsync();
            foreach (DocumentSnapshot docsnap in snap2)
            {
                if (docsnap.Exists)
                {
                    m.Working_statues = docsnap.GetValue<string>("Statues");
                    if (m.Working_statues.Equals(_worked))
                    {

                        return true;
                    }
                    else 
                    {
                     //   m.Working_statues = _unworked; 
                        return false;
                    }
                }
            }
            return false;

        }
        */







        public async Task<string> GetNearestMachine(string City, string Area,string Street)
        {
            Query Q = db.Collection("Machines");
            QuerySnapshot snap2 = await Q.GetSnapshotAsync();
            foreach (DocumentSnapshot docsnap in snap2)
            {
                if (docsnap.Exists)
                {
                    Dictionary<string, object> dic = docsnap.ToDictionary();
                    foreach (var item in dic)
                    {
                        if (item.Key.Equals("Address"))
                        {
                            foreach (var add in (Dictionary<string, object>)item.Value)
                            {
                                if (add.Key.Equals("City"))
                                {
                                    foreach (var area in (Dictionary<string, object>)item.Value)
                                    {
                                        if (area.Key.Equals("Area"))
                                        {
                                            foreach (var street in (Dictionary<string, object>)item.Value)
                                            {
                                                if (street.Key.Equals("Streert"))
                                                {                                                   
                                                    if (add.Value.Equals(City) && area.Value.Equals(Area) && street.Value.Equals(Street))
                                                    {
                                                        return add.Value + "," + area.Value + "," + street.Value;
                                                    }                              
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return null;
        }
        public async Task<bool> Check_For_National_id(string National_id)
        {
            Voter voter = new Voter();
            Query Q = db.Collection("Voters").WhereEqualTo("National_ID",National_id);
            QuerySnapshot snap = await Q.GetSnapshotAsync();
            foreach (DocumentSnapshot docsnap in snap)
            {
                if (docsnap.Exists)
                {
                    voter.National_id=docsnap.GetValue<string>("National_ID");
                    if (voter.National_id.Equals(National_id)) return true;
                    else  return false;
                }
            }
            return false;
        }
        //-----------------------------------------------------------------------------------------------
        //Admin
        public Dictionary<string,object> Map_Admin(Admin adm)
        {
            Dictionary<string, object> dic = new Dictionary<string, object>();
            dic.Add("National_id", adm.National_id);
            dic.Add("Username", adm.User_name);
            dic.Add("Password", adm.password);
            return dic;
        }
        public void Add_Admin(Admin ad)
        {
            DocumentReference doc = db.Collection("Admins").Document("za8J7fiuvGQW0Cj2oylJ");
            Dictionary<string, object> dic = Map_Admin(ad);
            doc.SetAsync(dic);
        }
        public async Task<Admin> Get_Admin_data(string user,string pass)
        {
            Admin admin = new Admin();
            Query Q = db.Collection("Admins");
            QuerySnapshot snap = await Q.GetSnapshotAsync();
            foreach(DocumentSnapshot docsnap in snap)
            {
                if (docsnap.Exists)
                {
                    admin.User_name = docsnap.GetValue<string>("Username");
                    admin.password = docsnap.GetValue<string>("Password");
                }
            }
            return admin;
        }
        public void delete_admin(string Fb_id)
        {
            DocumentReference doc = db.Collection("Admins").Document(Fb_id);
            doc.DeleteAsync();
        }
        public void update_admin(string Fb_id,Admin admin)
        {
            DocumentReference doc = db.Collection("Admins").Document(Fb_id);
            Dictionary<string, object> dic = new Dictionary<string, object>();
            dic.Add("National_id", admin.National_id);
            dic.Add("Username", admin.User_name);
            dic.Add("Password", admin.password);
            doc.UpdateAsync(dic);
        }
        public async Task<bool> Check_For_Admin_Data(string user, string pass)
        { 
            Query Q = db.Collection("Admins").WhereEqualTo("Username",user).WhereEqualTo("Password",pass);
            QuerySnapshot snap = await Q.GetSnapshotAsync();
            foreach (DocumentSnapshot docsnap in snap)
            {
                if (docsnap.Exists)
                {
                    Admin admin = new Admin();
                    admin.User_name = docsnap.GetValue<string>("Username");
                    admin.password = docsnap.GetValue<string>("Password");
                    if (admin.User_name.Equals(user) && admin.password.Equals(pass)) return true;
                    else return false;
                }
            }
            return false;
        }
        //------------------------------------------------------------------------------------------------------------
        //Request_Disabled_People
        public Dictionary<string,object> Map_Disabled_Requests(Disabled_People disabled)
        {
            Dictionary<string, object> dic = new Dictionary<string, object>();
            dic.Add("National_id", disabled.National_id);
            dic.Add("Phone", disabled.Phone);
            dic.Add("City", disabled.city);
            dic.Add("Link_Required_Paper", disabled.link);
            return dic;
        }
        public void Add_Disable_Request(Disabled_People disabled)
        {
            CollectionReference doc = db.Collection("Disabled"); 
            Dictionary<string, object> dic = Map_Disabled_Requests(disabled);
            doc.AddAsync(dic);
        }
        public void Delete_Disabled_Request(string Fb_id)
        {
            DocumentReference doc = db.Collection("Disabled").Document(Fb_id);
            doc.DeleteAsync();
        }
        public async Task<List<Disabled_People>> Retrieve_Disabled_Requests()
        {
            List<Disabled_People> disabled_list = new List<Disabled_People>();
            Query Q = db.Collection("Disabled");
            QuerySnapshot snap = await Q.GetSnapshotAsync();
            foreach (DocumentSnapshot docsnap in snap)
            {
                Disabled_People disabled = new Disabled_People();
                if (docsnap.Exists)
                    {
                    disabled.Fb_id = docsnap.Id;
                    disabled.National_id = docsnap.GetValue<string>("National_id");
                    disabled.Phone = docsnap.GetValue<string>("Phone");
                    disabled.city = docsnap.GetValue<string>("City");
                    disabled.link = docsnap.GetValue<string>("Link_Required_Paper");
                    disabled_list.Add(disabled);
                }
            }
            return disabled_list;
        }
        public void Apply_Disabled_Request(Disabled_People disabled)
        {
            Add_Disable_Request(disabled);
        }
        //-----------------------------------------------------------------------------------------------------------
        //Request_Candidate
        public Dictionary<string, object> Map_Candidate(Request_Candidates c)
        {
            Dictionary<string, object> dic = new Dictionary<string, object>();
            dic.Add("Image_Link", c.image_link);
            dic.Add("National_id", c.National_id);
            dic.Add("Email", c.Email);
            dic.Add("Phone", c.Phone);
            dic.Add("Link_Required_Paper", c.link_requied_paper);    
            return dic;
        }
        public void Add_Candidate_Request(Request_Candidates c)
        {
            CollectionReference doc = db.Collection("Request_Candidates");
            Dictionary<string, object> dic = Map_Candidate(c);
            doc.AddAsync(dic);
        }
        public void Delete_Candidate_Request(string Fb_id)
        {
            DocumentReference doc = db.Collection("Request_Candidates").Document(Fb_id);
            doc.DeleteAsync();
        }
        public async Task<List<Request_Candidates>> Retrieve_Candidates_Request()
        {
            List<Request_Candidates> req = new List<Request_Candidates>();
            Query Q = db.Collection("Request_Candidates");
            QuerySnapshot snap = await Q.GetSnapshotAsync();
            foreach(DocumentSnapshot docsnap in snap)
            {
                if (docsnap.Exists)
                {
                    Request_Candidates request = new Request_Candidates();
                    request.Fb_id = docsnap.Id;
                    request.National_id = docsnap.GetValue<string>("National_id");
                    request.Email = docsnap.GetValue<string>("Email");
                    request.Phone = docsnap.GetValue<string>("Phone");
                    request.link_requied_paper = docsnap.GetValue<string>("Link_Required_Paper");
                    req.Add(request);
                }
            }
            return req;
        }

        public async Task<Request_Candidates> Candidate_Request(string nationalID)
        {
            Request_Candidates request= new Request_Candidates(); 
            Query Q = db.Collection("Request_Candidates").WhereEqualTo("National_id",nationalID);
            QuerySnapshot snap = await Q.GetSnapshotAsync();
            foreach (DocumentSnapshot docsnap in snap)
            {
                if (docsnap.Exists)
                {
                    request.Fb_id = docsnap.Id;
                    request.National_id = docsnap.GetValue<string>("National_id");
                    request.Email = docsnap.GetValue<string>("Email");
                    request.Phone = docsnap.GetValue<string>("Phone");
                    request.link_requied_paper = docsnap.GetValue<string>("Link_Required_Paper");
                }
            }
            return request;
        }
        public void Apply_Candidate_Request(Request_Candidates c)
        {
            Official_Candidates off = new Official_Candidates();
            off.candidate_name = c.Email.Split('@')[0];
            off.Fb_id = c.Fb_id;
            off.pdf_link = c.link_requied_paper;
            off.Num_Of_Votes = 0;
            off.image_link = c.image_link;     
            Add_Official_Candidates(off);
            Delete_Candidate_Request(c.Fb_id);
        }
        public async Task<bool> CheckStatusForRequestCandidate(string status,string national_id)
        {
            string accepted = "مقبول";
            Request_Candidates _request = new Request_Candidates();
            Query Q = db.Collection("Request_Candidates").WhereEqualTo("National_id", national_id);
            QuerySnapshot snap = await Q.GetSnapshotAsync();
            foreach (DocumentSnapshot docsnap in snap)
            {
                if (docsnap.Exists)
                {
                    _request.Fb_id = docsnap.Id;
                    _request.National_id = docsnap.GetValue<string>("National_id");
                    _request.Email = docsnap.GetValue<string>("Email");
                    _request.link_requied_paper = docsnap.GetValue<string>("Link_Required_Paper");
                    _request.Phone = docsnap.GetValue<string>("Phone");
                    _request.image_link = docsnap.GetValue<string>("Image_Link");
                    if (status.Equals(accepted))
                    {
                        Apply_Candidate_Request(_request);
                    }
                    else {
                        Delete_Candidate_Request(_request.Fb_id);
                    }
                }
            }
            return true;
        }
        //------------------------------------------------------------------------------
        public async Task<List<Machine>> Retrieve_Machines()
        {
            Dictionary<string, object> add = new Dictionary<string, object>();
            List<Machine> machines = new List<Machine>();
            Query Q = db.Collection("Machines");
            QuerySnapshot snap = await Q.GetSnapshotAsync();
            foreach (DocumentSnapshot docsnap in snap)
            {
                Machine machine = new Machine();
                if (docsnap.Exists)
                {
                    machine.Fb_id = docsnap.Id;
                    machine.id = docsnap.GetValue<string>("Id");
                    machine.Model = docsnap.GetValue<string>("Model");
                    add= docsnap.GetValue<Dictionary<string, object>>("Address"); 
                    Address a = new Address();
                    a.streert_name = add["Streert"].ToString();
                    a.area = add["Area"].ToString();
                    a.city = add["City"].ToString();
                    machine.address = a;
                    machine.Working_statues = docsnap.GetValue<string>("Statues");
                    machines.Add(machine);
                }
            }
            return machines;
        }
        //---------------------------------------------------------------------------------------------------------   
        //On_OFF_Voting
        /*
        public async void Check_Voting()
            {
                Query Q = db.Collection("Result");
                QuerySnapshot snap = await Q.GetSnapshotAsync();
                foreach(DocumentSnapshot docsanp in snap)
                {
                    if (docsanp.Exists)
                    {
                        bool status = docsanp.ContainsField("finishd");
                        if(status.Equals("true"))
                            Console.WriteLine("Operation Finished");
                        else
                            Console.WriteLine("Operation Not Finished");
                    }
                }
            }
            public void Update_status(bool status)
            {
                DocumentReference doc = db.Collection("Result").Document("1");
                Dictionary<string, object> dic = new Dictionary<string, object>();
                dic.Add("finished", status);
                doc.UpdateAsync(dic);
            }
            public void Finish_operation()
            {
                Update_status(true);
            }
            public void Rest_operation()
            {
                Update_status(false);
            }
        */
        //---------------------------------------------------------------------------------------------
        public Dictionary<string, object> Map_Voter(Voter v)
        {
            Dictionary<string, object> dic = new Dictionary<string, object>();
            Dictionary<string, object> address = Map_Address(v.Address);
            dic.Add("National_ID", v.National_id);
            dic.Add("Name", v.Name);
            dic.Add("Marital_status", v.Marital_status);
            dic.Add("Job", v.Job);
            dic.Add("Qualifications", v.Qualifications);
            dic.Add("Religion", v.Religion);
            dic.Add("Phone", v.Phone_Number);
            dic.Add("Address", address);
            return dic;
        }
        public Dictionary<string, object> Map_Address(Address a)
        {
            Dictionary<string, object> addr = new Dictionary<string, object>();
            Dictionary<string, object> dic = new Dictionary<string, object>();
            dic.Add("House_Number", a.house_number);
            dic.Add("Area", a.area);
            dic.Add("City", a.city);
            dic.Add("Street_Name", a.streert_name);
            return dic;
        }
        public void Add_Voter(Voter v)
        {
            CollectionReference doc = db.Collection("Voters");
            Dictionary<string, object> dic = Map_Voter(v);
            doc.AddAsync(dic);
        }
        public async Task<Voter> Get_Voter_Data(string National_id)
        {
            Dictionary<string, object> add = new Dictionary<string, object>();
            Voter v = new Voter();
            Query Q = db.Collection("Voters").WhereEqualTo("National_ID", National_id);
            QuerySnapshot snap = await Q.GetSnapshotAsync();
            foreach (DocumentSnapshot docsnap in snap)
            {
                if (docsnap.Exists)
                {
                    v.Fb_id = docsnap.Id;
                    v.Name = docsnap.GetValue<string>("Name");
                    v.National_id = docsnap.GetValue<string>("National_ID");
                    add = docsnap.GetValue<Dictionary<string, object>>("Address");
                    Address a = new Address();
                    a.house_number = int.Parse(add["House Number"].ToString());
                    a.streert_name = add["Streert Name"].ToString();
                    a.area = add["Area"].ToString();
                    a.city = add["City"].ToString();
                    v.Address = a;
                    v.Job = docsnap.GetValue<string>("Job");
                    v.Gender = docsnap.GetValue<string>("Gender");
                    v.Religion = docsnap.GetValue<string>("Religion");
                    v.Marital_status = docsnap.GetValue<string>("Marital_status");
                    v.RightToVote = await Allow_To_Vote(National_id);
                    if(await AreVotingOrNot(National_id))
                    {
                        string statofvote = "انتخب";
                        v.Status_Of_Voting = statofvote;
                    }else
                        v.Status_Of_Voting = "لم ينتخب بعد";
                }
            }
            return v;
        }

        public async Task<List<Official_Candidates>> Get_Candidate(string Name)
        {
            List<Official_Candidates> lst = new List<Official_Candidates>();
            Official_Candidates c = new Official_Candidates();
            Query Q = db.Collection("Official Candidates").WhereEqualTo("Name", Name);
            QuerySnapshot snap = await Q.GetSnapshotAsync();
            foreach (DocumentSnapshot docsnap in snap)
            {
                if (docsnap.Exists)
                {
                    c.Fb_id = docsnap.Id;
                    c.candidate_name = docsnap.GetValue<string>("Name");
                    c.image_link = docsnap.GetValue<string>("Image_Link");
                    c.pdf_link = docsnap.GetValue<string>("PDF_Link");
                    lst.Add(c);
                }
            }
            return lst;
        }

        public Dictionary<string, object> Map_Official_Candidates(Official_Candidates off)
        {
            Dictionary<string, object> dic = new Dictionary<string, object>();
            dic.Add("Name", off.candidate_name);
            dic.Add("Image_Link", off.image_link);
            dic.Add("PDF_Link", off.pdf_link);
            dic.Add("Num_Of_Votes", off.Num_Of_Votes);
            return dic;
        }
        public void Add_Official_Candidates(Official_Candidates off)
        {
            CollectionReference doc = db.Collection("Official Candidates");
            Dictionary<string, object> dic = Map_Official_Candidates(off);
            doc.AddAsync(dic);
        }
        public async Task<List<Official_Candidates>> Retrieve_Official_Candidates()
        {
            List<Official_Candidates> lst_official = new List<Official_Candidates>();
            Query Q = db.Collection("Official Candidates");
            QuerySnapshot snap = await Q.GetSnapshotAsync();   
            foreach (DocumentSnapshot docsnap in snap)
            {
                Official_Candidates off = new Official_Candidates();
                if (docsnap.Exists)
                {
                    off.Fb_id = docsnap.Id;
                    off.image_link = docsnap.GetValue<string>("Image_Link");
                    off.candidate_name = docsnap.GetValue<string>("Name");
                    off.pdf_link = docsnap.GetValue<string>("PDF_Link");
                    lst_official.Add(off);
                }
            }
            return lst_official;
        }

        public async Task<List<Official_Candidates>> Retrieve_Official_Candidates_Ranked()
        {
            List<Official_Candidates> lst_official = new List<Official_Candidates>();
            Query Q = db.Collection("Official Candidates").OrderByDescending("Num_Of_Votes");
            QuerySnapshot snap = await Q.GetSnapshotAsync();
            foreach (DocumentSnapshot docsnap in snap)
            {
                Official_Candidates off = new Official_Candidates();
                if (docsnap.Exists)
                {
                    off.Fb_id = docsnap.Id;
                    off.image_link = docsnap.GetValue<string>("Image_Link");
                    off.candidate_name = docsnap.GetValue<string>("Name");
                    off.pdf_link = docsnap.GetValue<string>("PDF_Link");
                    lst_official.Add(off);
                }
            }
            return lst_official;
        }

    }
}