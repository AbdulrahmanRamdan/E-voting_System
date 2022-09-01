using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.IO;
using System.Drawing;
using System.Drawing.Imaging;

namespace myGP.Helper
{
    public class Image_Helper
    {
        public string convertfrom_Bitmap_to_string(Bitmap tempBitmap)
        {
            MemoryStream objStream = new MemoryStream();
            tempBitmap.Save(objStream, ImageFormat.Jpeg);
            return Convert.ToBase64String(objStream.ToArray());
        }
        public Bitmap convertto_Bitmap_from_string(string base64String)
        {
            // Convert base 64 string to byte[]
            byte[] imageBytes = Convert.FromBase64String(base64String);
            // Convert byte[] to Image
            using (var ms = new MemoryStream(imageBytes, 0, imageBytes.Length))
            {
                Bitmap image = (Bitmap)Image.FromStream(ms,true);
                return image;
            }
        }
    }
}