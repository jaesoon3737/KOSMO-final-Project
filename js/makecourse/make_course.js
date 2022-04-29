
// 코스 설명 글자수 제한
$(document).ready(function() {
    $('#course_info').on("keydown", function() {
        let content = $(this).val();
        if(content.length >= 100) {
            $(this).val(content.substring(0,100));
            $('#content_info_count').text("100/최대 100자");
        }else {
            $('#content_info_count').text(content.length + "/100");
        }
    });
    $('#course_info').on("keyup", function() {
        let content = $(this).val();
        if(content.length >= 100) {
            $(this).val(content.substring(0,100));
            $('#content_info_count').text("100/최대 100자");
        }else {
            $('#content_info_count').text(content.length + "/100");
        }
    });
});
