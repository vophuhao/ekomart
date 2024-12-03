// <!--nhúng js bootstrap -->
// src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
// integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
// crossorigin="anonymous"
//

// phần upload ảnh
    function chooseFile(fileInput, imageId) {
    if (fileInput.files && fileInput.files[0]) {
    var reader = new FileReader();
    reader.onload = function(e) {
    $('#' + imageId).attr('src', e.target.result);
};
    reader.readAsDataURL(fileInput.files[0]);
}
}

<!--	JavaScript check nếu đồng ý điều khoản thì mới cho bấm nút hoàn tất, không thì nohope -->
    $(document).ready(function () {
    //using jQuery code
    $('#confirm').on('change', function (e) {
        if (e.currentTarget.checked) {
            $('#submit').prop('disabled', false); // Enable the submit button
        } else {
            $('#submit').prop('disabled', true); // Disable the submit button
        }
    })
})
