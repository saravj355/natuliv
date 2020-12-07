// show/hide button back navbar administration
$(document).ready(function () {
    const windowURL = window.location.href;

    if (windowURL.indexOf('Natuliv/admin/') > -1) {
        $('#hide-this').css('display', 'block');
    } else {
        $('#hide-this').css('display', 'none');
        $('#logo-margin').css('margin-left', '6.5em');
    }

    $('#collapse').collapse("hide");
});

var loadFile = function (event) {
    const imageRender = document.getElementById('image-render');
    imageRender.src = URL.createObjectURL(event.target.files[0]);
};

