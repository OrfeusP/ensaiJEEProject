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

    $.get('/myapp/DisplayResultServlet', {
        id: window.location.search.split('=')[1]
    }, function(data, status){

        $('#program-title').html(data.filename);
        $('#program-results').html(data.result);
        editor.setValue(data.code);
    });
};