//On load
$(function () {
    // Default: hide edit mode
    $(".editMode").hide();

    // Click on "selectall" box
    $("#selectall").click(function () {
        $('.cb').prop('checked', this.checked);
    });

    // Click on a checkbox
    $(".cb").click(function () {
        if ($(".cb").length == $(".cb:checked").length) {
            $("#selectall").prop("checked", true);
        } else {
            $("#selectall").prop("checked", false);
        }
        if ($(".cb:checked").length != 0) {
            $("#deleteSelected").enable();
        } else {
            $("#deleteSelected").disable();
        }
    });

});


// Function setCheckboxValues
(function ($) {

    $.fn.setCheckboxValues = function (formFieldName, checkboxFieldName) {

        var ids = $('.' + checkboxFieldName + ':checked').map(function () {
            var input = document.createElement('input');

            var type = document.createAttribute('type');
            var id = document.createAttribute('value');
            var name = document.createAttribute('name');

            type.value = 'hidden';
            id.value = this.value;
            name.value = formFieldName;

            input.setAttributeNode(type);
            input.setAttributeNode(id);
            input.setAttributeNode(name);

            return input;
        });

        $(this).append(ids);

        return this;
    };

}(jQuery));

// Function toggleEditMode
(function ($) {

    $.fn.toggleEditMode = function () {
        if ($(".editMode").is(":visible")) {
            $(".editMode").hide();
            $("#editComputer").text("Edit");
        } else {
            $(".editMode").show();
            $("#editComputer").text("View");
        }
        return this;
    };

}(jQuery));


// Function delete selected: Asks for confirmation to delete selected computers, then submits it to the deleteForm
(function ($) {
    $.fn.deleteSelected = function () {
        if (confirm("Are you sure you want to delete the selected computers?")) {
            $('#deleteForm').setCheckboxValues('selection', 'cb');
            $('#deleteForm').submit();
        }
    };
}(jQuery));


//Event handling
//Onkeydown
$(document).keydown(function (e) {

    switch (e.keyCode) {
        //DEL key
        case 46:
            if ($(".editMode").is(":visible") && $(".cb:checked").length != 0) {
                $.fn.deleteSelected();
            }
            break;
        //E key (CTRL+E will switch to edit mode)
        case 69:
            if (e.ctrlKey) {
                $.fn.toggleEditMode();
            }
            break;
    }
});

function goSearch() {
    var search = $('#searchbox').val();
    setParameter('search', search);
    goPage(1);
    return false;
}

function getParameter(name) {
    return $("#parameters input[name|='" + name + "']").val();
}

function setParameter(name, val) {
    $("#parameters input[name|='" + name + "']").val(val);
}

function load() {
    var index = getParameter('index');
    var size = getParameter('size');
    var search = getParameter('search');

    var url = "dashboard?page=" + index + "&size=" + size;
    if (search) {
        url += "&search=" + search;
    }

    window.location.href = encodeURI(url);
    return false;
}

function goSize(size) {
    setParameter('size', size);
    load();
    return false;
}

function goPage(index) {
    setParameter('index', index);
    load();
    return false;
}