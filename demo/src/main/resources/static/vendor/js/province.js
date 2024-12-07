$(document).ready(function () {
    // Load provinces when the page is ready
    loadProvinces();

    // Khi tỉnh được chọn
    $('#tinh').change(function () {
        var provinceId = $(this).val();
        $('#huyen').empty().append('<option selected disabled>Select District</option>'); // Xóa quận cũ
        $('#phuong').empty().append('<option selected disabled>Select Ward</option>'); // Xóa phường cũ
        if (provinceId) {
            loadDistricts(provinceId); // Gọi API để lấy quận huyện
        }
    });

    // Khi quận được chọn
    $('#huyen').change(function () {
        var districtId = $(this).val();
        $('#phuong').empty().append('<option selected disabled>Select Ward</option>'); // Xóa phường cũ
        if (districtId) {
            loadWards(districtId); // Gọi API để lấy phường xã
        }
    });

    // Kiểm tra và tự động load quận khi tỉnh đã được chọn sẵn
    const selectedProvinceId = $('#tinh').val();
    if (selectedProvinceId) {
        loadDistricts(selectedProvinceId); // Gọi hàm loadDistricts nếu đã có tỉnh được chọn
    }
});

// Hàm load tỉnh thành
const loadProvinces = () => {
    fetch(`http://localhost:8888/api/v1/vendor/provinces`)
        .then(resp => resp.json())
        .then(provinces => {
            let selectedProvinceId = $("#myProvinceId").val();
            provinces.forEach(item => {
                const isSelected = item.id == selectedProvinceId ? 'selected' : '';
                $('#tinh').append(
                    `<option value="${item.id}" ${isSelected}>${item.slug}</option>`
                );
            });

            // Gọi loadDistricts sau khi tỉnh đã được load để kiểm tra và load quận nếu đã có tỉnh được chọn
            const selectedProvinceIdAfterLoad = $('#tinh').val();
            if (selectedProvinceIdAfterLoad) {
                loadDistricts(selectedProvinceIdAfterLoad);
            }
        });
};

// Hàm load quận huyện
const loadDistricts = (provinceId) => {
    fetch(`http://localhost:8888/api/v1/vendor/districts/${provinceId}`)
        .then(resp => resp.json())
        .then(districts => {
            const selectedDistrictId = $("#myDistrictId").val();
            $('#huyen').empty();  // Clear the previous district options first
            if (districts.length > 0) {
                $('#huyen').append('<option selected disabled>Select District</option>');
                districts.forEach(item => {
                    const isSelected = item.id == selectedDistrictId ? 'selected' : '';
                    $('#huyen').append(
                        `<option value="${item.id}" ${isSelected}>${item.name}</option>`
                    );
                });
            } else {
                $('#huyen').append('<option disabled>No districts found</option>');
            }
            // Kiểm tra và tự động load xã/phường nếu có quận đã được chọn
            const selectedDistrictIdAfterLoad = $('#huyen').val();
            if (selectedDistrictIdAfterLoad) {
                loadWards(selectedDistrictIdAfterLoad);
            }
        })
        .catch(error => {
            console.error('Error fetching districts:', error);
            $('#huyen').append('<option disabled>Error loading districts</option>');
        });
};

// Hàm load phường xã
const loadWards = (districtId) => {
    fetch(`http://localhost:8888/api/v1/vendor/wards/${districtId}`)
        .then(resp => resp.json())
        .then(wards => {
            const selectedWardId = $("#myWardId").val();
            $('#phuong').empty(); // Clear previous wards
            if (wards.length > 0) {
                $('#phuong').append('<option selected disabled>Select Ward</option>');
                wards.forEach(item => {
                    const isSelected = item.id == selectedWardId ? 'selected' : '';
                    $('#phuong').append(
                        `<option value="${item.id}" ${isSelected}>${item.name}</option>`
                    );
                });
            } else {
                $('#phuong').append('<option disabled>No wards found</option>');
            }
        })
        .catch(error => {
            console.error('Error fetching wards:', error);
            $('#phuong').append('<option disabled>Error loading wards</option>');
        });
};
