

function toEN_toAR() {


    
/* IDs of elements: 
 
    ***Navbar***
       navbar_management
       navbar_serviceForDisabled
       navbar_applyForPrisedency
       navbar_electionPrograms
       navbar_nearestMachine
       navbar_personalInfo
       navbar_Home
       navbar_callUs
       navbarDropdown


    ***Home***
    home_hello
    home_inquiry
    home_enterID
    home_candidatePrograms
    home_nearestMachine
    home_serviceForDisabled
    home_serviceForDisabled
    home_sponsored home_CS


   ***candidatePrograms***
   candidateElectionTitle

   ***enterAddress***
   nearestT1
   nearestT2
   cityValue
   areaValue
   streetValue
   searchAddress



    */

    //Navbar

    var y1 = document.getElementById("navbar_management");
    var y2 = document.getElementById("navbar_serviceForDisabled");
    var y3 = document.getElementById("navbar_applyForPrisedency");
    var y4 = document.getElementById("navbar_electionPrograms");
    var y5 = document.getElementById("navbar_nearestMachine");
    var y6 = document.getElementById("navbar_personalInfo");
    var y7 = document.getElementById("navbar_Home");
    var y8 = document.getElementById("navbar_callUs");
    var y9 = document.getElementById("navbarDropdown");
    //Home
    var x1 = document.getElementById("home_hello");
    var x2 = document.getElementById("home_inquiry");
    var x3 = document.getElementById("home_enterID");
    var x4 = document.getElementById("home_candidatePrograms");
    var x5 = document.getElementById("home_nearestMachine");
    var x6 = document.getElementById("home_serviceForDisabled");
    var x7 = document.getElementById("home_sponsored");
    var x8 = document.getElementById("home_CS");
    //candidatePrograms
    var a1 = document.getElementById("candidateElectionTitle");
    //enterAddress

    var b1 = document.getElementById("nearestT1");
    var b2 = document.getElementById("nearestT2");
    var b3 = document.getElementById("cityValue");
    var b4 = document.getElementById("areaValue");
    var b5 = document.getElementById("streetValue");
    var b6 = document.getElementById("searchAddress");
    
   

    var eng_ar_Button = document.getElementById("EN_AR");

    if (eng_ar_Button.innerHTML == "English") {

        eng_ar_Button.innerHTML = "العربية"
        //navbar
        y1.innerHTML = "Administration";
        y2.innerHTML = "Services for Disabled";
        y3.innerHTML = "Apply for Presidency";
        y4.innerHTML = "Election Programs";
        y5.innerHTML = "Nearest Machine";
        y6.innerHTML = "Personal Info";
        y7.innerHTML = "Home";
        y8.innerHTML = "Call Us";
        y9.innerHTML = "Inquiry for";
        //home
        x1.innerHTML = "Welcome to the official site of the Presidential Election";
        x2.innerHTML = "For Inquiry";
        x3.innerHTML = "Enter your National ID";
        x4.innerHTML = "Election Programs";
        x5.innerHTML = "Nearest Voting Machine";
        x6.innerHTML = "Services for Disabled";
        x7.innerHTML = "Sponsored by"
        x8.innerHTML = "Faculty of Computer and Information Sciences";

        
        //candidatePrograms
        a1.innerHTML = "Election programs for presidential candidates for the year 2022"

        //enterAddress
        b1.innerHTML = "To find the nearest voting machine to you";
        b2.innerHTML = "Enter your current address";
        b3.innerHTML = "enter city";
        b4.innerHTML = "enter area";
        b5.innerHTML = "enter street";
        b6.innerHTML = "Search";

    }
    else if (eng_ar_Button.innerHTML == "العربية") {
        eng_ar_Button.innerHTML = "English";
        x1.innerHTML = "مرحبا بكم فى الموقع الرسمى للإنتخابات الرئاسية";
        x2.innerHTML = "للاستعلام";
        x3.innerHTML = "ادخل الرقم القومي";
        x4.innerHTML = "البرامج الإنتخابية";
        x5.innerHTML = "اقرب ماكينة تصويت";
        x6.innerHTML = "خدمات لذوي الهمم";
        x7.innerHTML = "تحت رعاية";
        x8.innerHTML = "كلية حاسبات و معلومات جامعة عين شمس";
    }





}