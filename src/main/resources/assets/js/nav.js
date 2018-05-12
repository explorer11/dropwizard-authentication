////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  Logout link
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

$('#logoutLink-modal').modal({
    blurring: true,
    onApprove: function () {
        $.ajax({
            type: 'POST',
            url: $('#logoutLink-modal input').val(),
            dataType : 'html',
            success: function () {
                document.location.reload(true);
            }
        });
    }
});

$('span.logout.item')
        .click(function () {
            $('#logoutLink-modal input').val('/logout');
            $('#logoutLink-modal div.header').text('Logout');
            $('#logoutLink-modal div.content p').text('You\'re about to log out. Any unsaved changes will be lost. Proceed ?');
            $('#logoutLink-modal').modal('show');
        })
;