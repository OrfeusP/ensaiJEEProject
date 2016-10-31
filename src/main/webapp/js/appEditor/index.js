window.onload = function () {

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

    $('.appEditor').find('input').each(function () {
        if ($(this).val() !== '') {
            $(this).prev('label').addClass('active');
        }
    });


    editor = CodeMirror(document.getElementById('code'), {
        lineNumbers: true,
        mode: 'r'
    });
};