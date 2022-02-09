export const ValidateMail=(mail) =>
{
    
        
    if (/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/.test(mail))
    {
      return (true)
    }
    else{
      return false;
    } 
}