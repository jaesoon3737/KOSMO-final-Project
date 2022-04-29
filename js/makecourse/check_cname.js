$(document).ready(function() {
    $('#course_name').on('focusout', function() {
        let courseName = $(this).val();
        $('#find_cname').empty();
        if(courseName.length != 0) {
	    	let csrfParameter = $('meta[name="_csrf_parameter"]').attr('content');
	        let csrfHeader = $('meta[name="_csrf_header"]').attr('content');
	        let csrfToken = $('meta[name="_csrf"]').attr('content');
            $.ajax({
                url: "/jejufriends/make_course/findcname.json",
                type: "POST",
                data: {cname: courseName},
                beforeSend: function(xhr) {
                    xhr.setRequestHeader(csrfHeader, csrfToken);
                },
                success: function(flag) {
                    if(flag) {
                        $('#find_cname').text('사용가능한 코스 이름 입니다.');
                        $('#find_cname').css('color', 'rgba(30,180,30,0.8)');
                    }else {
                        $('#find_cname').text('이미 사용중인 코스 이름 입니다.');
                        $('#find_cname').css('color', 'rgba(255,0,0,0.8)');
                    }
                }
            });
        }
        
    })
});