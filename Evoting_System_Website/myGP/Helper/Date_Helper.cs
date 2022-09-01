//using Google.Type;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Web;


namespace myGP.Helper
{
    public class Date_Helper
    {
        public string ConvertDateToString(DateTime dt)
        {
            string date_string = dt.ToString();
            return date_string;
        }
        public DateTime ConvertToDate_FromString(string st)
        {
            DateTime d = DateTime.ParseExact(st, "dd/MM/yyyy", CultureInfo.InvariantCulture);
            return d;
        }
        public bool Check_Date(DateTime dt)
        {
            DateTime date = new DateTime();
            if ((date.Year - dt.Year) >= 18)
                return true;
            else
                return false;
        }
    }
}