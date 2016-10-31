window.onload = function () {

    $('.form').find('input, textarea').on('keyup blur focus', function (e) {

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

    $('.form').find('input, textarea').each(function () {
        if ($(this).val() !== '') {
            $(this).prev('label').addClass('active');
        }
    });

    $('.tab a').on('click', function (e) {

        e.preventDefault();

        $(this).parent().addClass('active');
        $(this).parent().siblings().removeClass('active');

        var target = $(this).attr('href');

        $('.tab-content > div').not(target).hide();

        $(target).fadeIn(600);

    });

    $('#login-btn').click(function () {
        $.post('/myapp/LoginServlet',
            {
                username: $('input[name=usernameLogin]').val(),
                password: $('input[name=passwordLogin]').val()
            })
            .fail(function () {
                alert('Failed to Login! Invalid Credentials!');
            });
    });

    $('#signup-btn').click(function () {
        $.post('/myapp/SubscribeServlet',
            {
                username: $('input[name=usernameSubscribe]').val(),
                password: $('input[name=passwordSubscribe]').val(),
                fname: $('input[name=fname]').val(),
                lname: $('input[name=lname]').val(),
                email: $('input[name=email]').val()
            })
            .fail(function () {
                alert('Failed to Login! Invalid Credentials!');
            });
    });

};