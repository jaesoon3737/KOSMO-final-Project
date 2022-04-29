let divisionCheck = 0;

$(document).ready(function() {
    $('.course_division').on("click", function() {
        const divisionText = $(this).attr('alt');
        // 사진 셋팅
        if(divisionCheck == divisionText) {
            $('#jejudo_map_img').attr("src", "/img/jejudo_full.png");
            divisionCheck = 0;
        }else if(divisionText == 1) {
            $('#jejudo_map_img').attr("src", "/img/jejudo_center.png");
            divisionCheck = divisionText;
        }else if(divisionText == 2) {
            $('#jejudo_map_img').attr("src", "/img/jejudo_west.png");
            divisionCheck = divisionText;
        }else if(divisionText == 3) {
            $('#jejudo_map_img').attr("src", "/img/jejudo_north.png");
            divisionCheck = divisionText;
        }else if(divisionText == 4) {
            $('#jejudo_map_img').attr("src", "/img/jejudo_east.png");
            divisionCheck = divisionText;
        }else if(divisionText == 5) {
            $('#jejudo_map_img').attr("src", "/img/jejudo_south.png");
            divisionCheck = divisionText;
        }
        
        let csrfParameter = $('meta[name="_csrf_parameter"]').attr('content');
        let csrfHeader = $('meta[name="_csrf_header"]').attr('content');
        let csrfToken = $('meta[name="_csrf"]').attr('content');
        $.ajax({
            url: "/jejufriends/select_course/selectDivision.json",
            type: "POST",
            data: {division: divisionCheck},
            beforeSend: function(xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function(courseList) {
                let pageNum = $('#pageNum option:selected').val();
                pageSet(pageNum, 1, courseList);
            }
        });
    });
});