window.onload = function () {


    function getCookie(cname) {
        var name = cname + "=";
        var ca = document.cookie.split(';');
        for(var i = 0; i <ca.length; i++) {
            var c = ca[i];
            while (c.charAt(0)==' ') {
                c = c.substring(1);
            }
            if (c.indexOf(name) == 0) {
                return c.substring(name.length,c.length);
            }
        }
        return "";
    }

    $("#firstName").html(getCookie('firstname'));
    $("#lastName").html(getCookie('lastname'));
    $(".email").html(getCookie('email'));

    var delete_cookie = function (name) {
        document.cookie = name + '=;expires=Thu, 01 Jan 1970 00:00:01 GMT;';
    };


    $('.logout').click(function () {
        var cookies = ['firstname','lastname','email'];
        for (var i = 0; i < cookies.length; i++) {
            delete_cookie(cookies[i]);
        }
        window.location.href = "index.html";
    });

    $.get('/myapp/LoadRprogamServlet', function(data, status){

        for(var i = 0, len = data.length ; i < len; i++){
            console.log(data[i]);
            $('.table').append("<tr data-program-id="+data[i].id+">"+
            "<td>"+(i+1)+"</td>"+
            "<td>"+data[i].filename+"</td>"+
            "<td>"+data[i].lastModified+"</td>"+
            "<td>"+data[i].created+"</td>"+
            "<td><a>Delete</a>&nbsp;<a>Edit</a></td>"+
            "</tr>");
        }

        $('.table').click(function (e) {
            e.preventDefault();
            if (e.target && e.target.nodeName === 'A') {
                if (e.target.innerText === 'Edit') {
                    var id =e.target.parentNode.parentNode.dataset['programId'];
                    window.location.href="/myapp/LoadToEditServlet/"+id;
                    console.log(e.target.parentNode.parentNode.dataset['programId']);
                } else if (e.target.innerText === 'Delete') {
                    var id =e.target.parentNode.parentNode.dataset['programId'];
                    console.log(e.target.parentNode.parentNode.dataset['programId']);
                    $.post('/myapp/LoadRprogamServlet',{
                      id:id
                    })
                    .done(function () {
                        $("tr[data-program-id='"+id+"']").remove();
                    });
                }
            }
        });
        console.log(status);
    })
        .fail(function (error) {
            alert(error);
        });
    $('.create-button').click(function (e) {
        window.location.href = "appEditor.html"

    })

}