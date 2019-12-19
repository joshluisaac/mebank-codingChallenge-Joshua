function buildUpdateTableEntry(rowId, first_name, last_name) {
    var output =
        "<td>" +
        first_name +
        '<input type="hidden" name="hidden_first_name[]" id="first_name' +
        rowId +
        '" class="first_name" value="' +
        first_name +
        '" /> </td>';

    output +=
        "<td>" +
        last_name +
        '<input type="hidden" name="hidden_last_name[]" id="last_name' +
        rowId +
        '" class="last_name" value="' +
        last_name +
        '" /> </td>';

    output +=
        '<td>  <button type="button" name="view_details" class="btn btn-btn-warning btn-sm view_details" id="' +
        rowId +
        '">View</button></td>';

    output +=
        '<td>  <button type="button" name="remove_details" class="btn btn-btn-danger btn-sm remove_details" id="' +
        rowId +
        '">Remove</button></td>';

    return output;
}


$(document).ready(function () {
    var counter = 0;
    $("#user_dialog").dialog({
        autoOpen: false,
        width: 400
    });

    //trigger this code block when add button is clicked
    $("#add").click(function () {
        $("#user_dialog").dialog("option", "title", "Add Data");
        $("#first_name").val("");
        $("#last_name").val("");
        $("#hiddenRowId").val("");
        $("#error_first_name").text("");
        $("#error_last_name").text("");
        $("#first_name").css("border-color", "");
        $("#last_name").css("border-color", "");
        $("#save").text("Save");
        $("#user_dialog").dialog("open");
    });

    $("#save").click(function () {
        var ErrorFirstNameValidationMessage = "";
        var ErrorLastNameValidationMessage = "";
        var first_name = "";
        var last_name = "";

        // if first name is blank/empty
        if ($("#first_name").val() == "") {
            ErrorFirstNameValidationMessage = "First Name is required";
            $("#error_first_name").text(ErrorFirstNameValidationMessage);
            $("#first_name").css("border-colour", "#cc0000");
            first_name = "";
        } else {
            ErrorFirstNameValidationMessage = "";
            $("#error_first_name").text(ErrorFirstNameValidationMessage);
            $("#first_name").css("border-colour", "");
            first_name = $("#first_name").val();
        }

        // if last name is blank/empty
        if ($("#last_name").val() == "") {
            ErrorLastNameValidationMessage = "Last Name is required";
            $("#error_last_name").text(ErrorLastNameValidationMessage);
            $("#last_name").css("border-colour", "#cc0000");
            last_name = "";
        } else {
            ErrorLastNameValidationMessage = "";
            $("#error_last_name").text(ErrorLastNameValidationMessage);
            $("#last_name").css("border-colour", "");
            last_name = $("#last_name").val();
        }

        if (
            ErrorFirstNameValidationMessage != "" ||
            ErrorLastNameValidationMessage != ""
        ) {
            return false;
        } else {
            if ($("#save").text() == "Save") {
                //increment counter
                counter = counter + 1;
                var output = buildUpdateTableEntry(counter, first_name, last_name);
                var content = '<tr id="rowKey' + counter + '">' + output + "</tr>";
                $("#user_data").append(content);
            } else {
                var rowId = $('#hiddenRowId').val();
                var output = buildUpdateTableEntry(rowId, first_name, last_name);
                $('#rowKey' + rowId).html(output);
            }
            $("#user_dialog").dialog("close");
        }
    });

    $(document).on("click", ".view_details", function () {
        //fetch rowId from current row
        var rowId = $(this).attr("id");

        //retrieve field values
        var first_nameRow = $('#first_name' + rowId).val();
        var last_nameRow = $('#last_name' + rowId).val();

        //copy to dialog form
        $('#first_name').val(first_nameRow);
        $('#last_name').val(last_nameRow);

        //update dialog box save button to edit
        $('#save').text("Edit");

        //update hidden row id on dialog form
        //copy rowId -> hiddenRowId
        $('#hiddenRowId').val(rowId);

        //update dialog window
        $('#user_dialog').dialog('option', 'title', 'Edit Data');

        $('#user_dialog').dialog('open');
    });


    $(document).on('click', '.remove_details', function () {
        //fetch rowId from current row
        var rowId = $(this).attr("id");
        if (confirm("Are you sure you want to remove this entry?")) {
            $('#rowKey' + rowId).remove();

        } else {
            return false;
        }

    });

});