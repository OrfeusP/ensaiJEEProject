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


    $('.appEditor').find('input').on('keyup blur focus', function (e) {

        var $this = $(this),
            label = $this.prev('label');

        if (e.type === 'keyup') {
            if ($this.val() === '') {
                label.removeClass('active highlight');
            } else {
                label.addClass('active highlight');
            }
        } else if (e.type === 'blur') {
            if ($this.val() === '') {
                label.removeClass('active highlight');
            } else {
                label.removeClass('highlight');
            }
        } else if (e.type === 'focus') {

            if ($this.val() === '') {
                label.removeClass('highlight');
            }
            else if ($this.val() !== '') {
                label.addClass('highlight');
            }
        }

    });



    editor = CodeMirror(document.getElementById('code'), {
        lineNumbers: true,
        mode: 'r'
    });
    $('.save-button').click(function (e) {

        $.post('/myapp/RinterpretationServlet',
            {
                id: window.location.search.split('=')[1],
                filename: $('input[name=programName]').val(),
                Rprogram:editor.getValue()
            })
        .done(function (data) {
            var id = data.id;
            window.location.href = "results.html?id="+id;
        })
        .fail(function () {
            alert('Failed to Login! Invalid Credentials!');
        });
    });

    $.get('/myapp/RinterpretationServlet', {
            id: window.location.search.split('=')[1]
    }, function(data, status){

        console.log(data.filename);
        console.log(data.code);

        $("#programName").val(data.filename);

        editor.setValue(data.code);
    });

};