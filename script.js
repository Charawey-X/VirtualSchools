function login(){
    const form = document.getElementById('login');
     var email = form.elements['email'].value;
     var pass = form.elements['password'].value;
     const data =  {email: email,password: pass};
     fetch("http://localhost:8989/api/v1/users/login",
        {
          method: 'POST',
          body: JSON.stringify(data)}
          )
        .then(function(res){
            if(res.status){

            }
         })
    }

    function school(){
        var school =document.getElementById('school').value;
        var institution =document.getElementById('institution').value;
        var administrator =document.getElementById('administrator').value;
        var email =document.getElementById('email').value;
        var SchoolEmail =document.getElementById('SchoolEmail').value;
        var location =document.getElementById('location').value;

        if(school==""||institution==""||administrator==""||email==""||SchoolEmail==""||location=="" ){
            alert('please input  the above fields!')
        }else{
            alert("Hello" + school)
        }

    }