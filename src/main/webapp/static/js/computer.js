(function ($) {
    $('#introduced').change(function () {
        $('#discontinued').attr('min', $(this).val());
    })
}(jQuery));