$(document).ready(function () {
    $(".basketFlowerCount").on('change', function () {
        const id = this.id;
        $('#update-item-'+id).css('display', 'inline-block');
    });
});