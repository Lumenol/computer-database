(function ($) {
    $('#introduced').change(function () {
        var val = $(this).val();
        if (val) {
            $('#discontinued').attr('min', val);
        } else {
            $('#discontinued').attr('min', '1970-01-01');
        }
    })
}(jQuery));