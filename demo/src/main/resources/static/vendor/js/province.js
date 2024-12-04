$(document).ready(function () {
    //Lấy tỉnh thành


    fetch(`http://localhost:8888/api/v1/vendor/provinces`)
        .then(resp => {
            return resp.json()
        })
        .then(province => {
            province.forEach((item) => {
                $('#tinh').append(
                    `<option value="${item.id}"> 
                ${item.slug}
              </option>`
                )

            })

        })

    // Load districts when a province is selected
    $('#tinh').change(function () {
        var provinceId = $(this).val();
        $('#huyen').empty().append('<option selected disabled>Select District</option>'); // Clear previous districts
        $('#phuong').empty().append('<option selected disabled>Select Ward</option>'); // Clear wards as well
        if (provinceId) {
            getDistrict(provinceId);
        }
    });

    // Load wards when a district is selected
    $('#huyen').change(function () {
        var districtId = $(this).val();
        $('#phuong').empty().append('<option selected disabled>Select Ward</option>'); // Clear previous wards
        if (districtId) {
            getWard(districtId);
        }
    });
});
const getDistrict = (provinceId) => {
    fetch(`http://localhost:8888/api/v1/vendor/districts/${provinceId}`)
        .then(resp => {
            return resp.json()
        })
        .then(district => {
            district.forEach((item) => {

                $('#huyen').append(
                    `<option value="${item.id}"> 
                ${item.name}
              </option>`
                )

            })
        })

}

const getWard = (districtId) => {
    fetch(`http://localhost:8888/api/v1/vendor/wards/${districtId}`)
        .then(resp => {
            return resp.json()
        })
        .then(ward => {
            console.log(ward)
            ward.forEach((item) => {
                $('#phuong').append(
                    `<option value="${item.id}"> 
                ${item.name}
              </option>`
                )
            })
        })
}

// $("#phuong option:selected").text()
