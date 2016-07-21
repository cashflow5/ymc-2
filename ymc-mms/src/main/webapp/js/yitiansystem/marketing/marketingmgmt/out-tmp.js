function fun1(){
	today = new Date();
    yesterday = new Date();
    houre = today.getHours();
    for (i=0;i<24;i++)
    {
        if(i==houre)
        {
        	  if (i < 10)
            {
                document.write('<OPTION VALUE="0' + i + '" SELECTED>0' + i );
            }   
            else
            {
                document.write('<OPTION VALUE="' + i + '"  SELECTED>' + i );
            }
        } 
        else 
        {
            if (i < 10)
            {
                document.write('<OPTION VALUE="0' + i + '">0' + i );
            }
            else
            {
                document.write('<OPTION VALUE="' + i + '">' + i );
            }
        }
    }
}

function fun2(){
	 today = new Date();
     yesterday = new Date();
     minute = today.getMinutes();


     for (i=0;i<60;i++)
     {
         if(i==minute)
         {
         	  if (i < 10)
             {
                 document.write('<OPTION VALUE="0' + i + '" SELECTED>0' + i );
             }   
             else
             {
                 document.write('<OPTION VALUE="' + i + '"  SELECTED>' + i );
             }
         } 
         else 
         {
             if (i < 10)
             {
                 document.write('<OPTION VALUE="0' + i + '">0' + i );
             }
             else
             {
                 document.write('<OPTION VALUE="' + i + '">' + i );
             }
         }
     }
}