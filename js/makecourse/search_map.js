let mapContainer = document.getElementById('map'),
    mapOption = { 
        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };

let map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

let geocoder = new kakao.maps.services.Geocoder();

function getInfo() {
    // 지도의 현재 중심좌표를 얻어옵니다 
    let center = map.getCenter(); 
    
    // 지도의 현재 레벨을 얻어옵니다
    let level = map.getLevel();
    
    // 지도타입을 얻어옵니다
    let mapTypeId = map.getMapTypeId(); 
    
    // 지도의 현재 영역을 얻어옵니다 
    let bounds = map.getBounds();
    
    // 영역의 남서쪽 좌표를 얻어옵니다 
    let swLatLng = bounds.getSouthWest(); 
    
    // 영역의 북동쪽 좌표를 얻어옵니다 
    let neLatLng = bounds.getNorthEast(); 
    
    // 영역정보를 문자열로 얻어옵니다. ((남,서), (북,동)) 형식입니다
    let boundsStr = bounds.toString();
    
    
    let message = '지도 중심좌표는 위도 ' + center.getLat() + ', ';
    message += '경도 ' + center.getLng() + ' 이고 ';
    message += '지도 레벨은 ' + level + ' 입니다';
    message += '지도 타입은 ' + mapTypeId + ' 이고';
    message += '지도의 남서쪽 좌표는 ' + swLatLng.getLat() + ', ' + swLatLng.getLng() + ' 이고';
    message += '북동쪽 좌표는 ' + neLatLng.getLat() + ', ' + neLatLng.getLng() + ' 입니다';
    
    // 개발자도구를 통해 직접 message 내용을 확인해 보세요.
}

/*
// 경로 선으로 그리는 
let distanceOverlay; // 선의 거리정보를 표시할 커스텀오버레이 입니다
let moveLine;
let pathList = []; 
let dots = []; // 선이 그려지고 있을때 클릭할 때마다 클릭 지점과 거리를 표시하는 커스텀 오버레이 배열입니다.
function drawPath() {
    
    for(let i = 0; i < pathList.length; i++) {
        moveLine = new kakao.maps.Polyline({
            map: map, // 선을 표시할 지도입니다 
            path: [pathList[i], pathList[i+1]], // 선을 구성하는 좌표 배열입니다 클릭한 위치를 넣어줍니다
            strokeWeight: 3, // 선의 두께입니다 
            strokeColor: '#db4040', // 선의 색깔입니다
            strokeOpacity: 1, // 선의 불투명도입니다 0에서 1 사이값이며 0에 가까울수록 투명합니다
            strokeStyle: 'solid' // 선의 스타일입니다 
        });
        console.log("moveLine.getLength(): " + moveLine.getLength());
        let distance = Math.round(moveLine.getLength()), // 선의 총 거리를 계산합니다
            content = '<div class="dotOverlay distanceInfo">총거리 <span class="number">' + distance + '</span>m</div>'; // 커스텀오버레이에 추가될 내용입니다
        
        // 거리정보를 지도에 표시합니다
        showDistance(content, pathList[i+1]);   
    
        displayCircleDot(pathList[i],distance);
        displayCircleDot(pathList[i+1],distance);

        let distancetime = Math.round(moveLine.getLength()), // 선의 총 거리를 계산합니다
            contentTime = getTimeHTML(distancetime); // 커스텀오버레이에 추가될 내용입니다
            
        // 그려진 선의 거리정보를 지도에 표시합니다
        showDistance(contentTime, pathList[i+1]);
    }
}

function showDistance(content, position) {
    
    if (distanceOverlay) { // 커스텀오버레이가 생성된 상태이면
        
        // 커스텀 오버레이의 위치와 표시할 내용을 설정합니다
        distanceOverlay.setPosition(position);
        distanceOverlay.setContent(content);
        
    } else { // 커스텀 오버레이가 생성되지 않은 상태이면
        
        // 커스텀 오버레이를 생성하고 지도에 표시합니다
        distanceOverlay = new kakao.maps.CustomOverlay({
            map: map, // 커스텀오버레이를 표시할 지도입니다
            content: content,  // 커스텀오버레이에 표시할 내용입니다
            position: position, // 커스텀오버레이를 표시할 위치입니다.
            xAnchor: 0,
            yAnchor: 0,
            zIndex: 3  
        });      
    }
}
// 그려진 선을 지도에서 제거하는 함수입니다
function deleteClickLine() {
    if (moveLine) {
        moveLine.setMap(null);    
        moveLine = null;        
    }
}
// 그려지고 있는 선의 총거리 정보와 
// 선 그리가 종료됐을 때 선의 정보를 표시하는 커스텀 오버레이를 삭제하는 함수입니다
function deleteDistnce () {
    if (distanceOverlay) {
        console.log();
        distanceOverlay.setMap(null);
        distanceOverlay = null;
    }
}


// 선이 그려지고 있는 상태일 때 지도를 클릭하면 호출하여 
// 클릭 지점에 대한 정보 (동그라미와 클릭 지점까지의 총거리)를 표출하는 함수입니다
function displayCircleDot(position, distance) {

    // 클릭 지점을 표시할 빨간 동그라미 커스텀오버레이를 생성합니다
    var circleOverlay = new kakao.maps.CustomOverlay({
        content: '<span class="dot"></span>',
        position: position,
        zIndex: 1
    });

    // 지도에 표시합니다
    circleOverlay.setMap(map);

    if (distance > 0) {
        // 클릭한 지점까지의 그려진 선의 총 거리를 표시할 커스텀 오버레이를 생성합니다
        var distanceOverlay = new kakao.maps.CustomOverlay({
            content: '<div class="dotOverlay">거리 <span class="number">' + distance + '</span>m</div>',
            position: position,
            yAnchor: 1,
            zIndex: 2
        });

        // 지도에 표시합니다
        distanceOverlay.setMap(map);
    }

    // 배열에 추가합니다
    dots.push({circle:circleOverlay, distance: distanceOverlay});
}

// 클릭 지점에 대한 정보 (동그라미와 클릭 지점까지의 총거리)를 지도에서 모두 제거하는 함수입니다
function deleteCircleDot() {
    var i;

    for ( i = 0; i < dots.length; i++ ){
        if (dots[i].circle) { 
            dots[i].circle.setMap(null);
        }

        if (dots[i].distance) {
            dots[i].distance.setMap(null);
        }
    }

    dots = [];
}

// 마우스 우클릭 하여 선 그리기가 종료됐을 때 호출하여 
// 그려진 선의 총거리 정보와 거리에 대한 도보, 자전거 시간을 계산하여
// HTML Content를 만들어 리턴하는 함수입니다
function getTimeHTML(distance) {

    // 도보의 시속은 평균 4km/h 이고 도보의 분속은 67m/min입니다
    var walkkTime = distance / 67 | 0;
    var walkHour = '', walkMin = '';

    // 계산한 도보 시간이 60분 보다 크면 시간으로 표시합니다
    if (walkkTime > 60) {
        walkHour = '<span class="number">' + Math.floor(walkkTime / 60) + '</span>시간 '
    }
    walkMin = '<span class="number">' + walkkTime % 60 + '</span>분'

    // 자전거의 평균 시속은 16km/h 이고 이것을 기준으로 자전거의 분속은 267m/min입니다
    var bycicleTime = distance / 227 | 0;
    var bycicleHour = '', bycicleMin = '';

    // 계산한 자전거 시간이 60분 보다 크면 시간으로 표출합니다
    if (bycicleTime > 60) {
        bycicleHour = '<span class="number">' + Math.floor(bycicleTime / 60) + '</span>시간 '
    }
    bycicleMin = '<span class="number">' + bycicleTime % 60 + '</span>분'

    // 거리와 도보 시간, 자전거 시간을 가지고 HTML Content를 만들어 리턴합니다
    var content = '<ul class="dotOverlay distanceInfo">';
    content += '    <li>';
    content += '        <span class="label">총거리</span><span class="number">' + distance + '</span>m';
    content += '    </li>';
    content += '    <li>';
    content += '        <span class="label">도보</span>' + walkHour + walkMin;
    content += '    </li>';
    content += '    <li>';
    content += '        <span class="label">자전거</span>' + bycicleHour + bycicleMin;
    content += '    </li>';
    content += '</ul>'

    return content;
}
*/


// 마커 표시 이벤트
let markers = [];
let infowindows = [];
let marker = new kakao.maps.Marker({
    map: null,
    position: null,
});
let infowindow = new kakao.maps.InfoWindow({
    content: null,
});
$(document).ready(function() {
    $(document).on('click', '.make_content', function() {
        console.log("컨텐츠 하나 클릭");

        deleteMarker();
        deleteInfoWindow();
        //deleteClickLine();
        //deleteCircleDot();
        //deleteDistnce();
        $('.distanceInfo').remove();
        const contentAddress = $(this).children('.content_input').val();
        const contentName = $(this).children('.prd-cont').children('.content_subject').text();
        geocoder.addressSearch(contentAddress, function(result, status) {
            console.log("좌표값 검색 전");
            console.log("contentAddress: " + contentAddress);
            // 정상적으로 검색이 완료됐으면 
             if (status === kakao.maps.services.Status.OK) {
                console.log("검색 성공");
                let coords = new kakao.maps.LatLng(result[0].y, result[0].x);
    
                // 결과값으로 받은 위치를 마커로 표시합니다
                let newMarker = new kakao.maps.Marker({
                    map: map,
                    position: coords
                });
                marker = newMarker;
        
                // 인포윈도우로 장소에 대한 설명을 표시합니다
                let newInfowindow = new kakao.maps.InfoWindow({
                    content: `<div style="width:150px;text-align:center;padding:6px 0;">${contentName}</div>`
                });
                infowindow = newInfowindow;
                infowindow.open(map, marker);
        
                // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
                map.setCenter(coords);
                map.setLevel(3); // 맵 확대 축소
                getInfo();
            } 
        });
    });

    // 일정에 들어있는 모든 컨텐츠 마커 표시 이벤트
    $(document).on('click', '.route', function() {
        deleteMarker();
        deleteInfoWindow();
        // deleteClickLine();
        // deleteCircleDot();
        // deleteDistnce();
        const lodgmentNum = $(this).siblings('.delete_input').val(); // 형제 태그 찾기
        const contentLength = $(`#course_date_${lodgmentNum}`).children().length;
        for(let i = 0; i < contentLength; i++) {
            // children() 은 첫번 재 자식만 찾고, find() 는 모든 자식, 손자 태그까지 찾는다. 
            let contentName = $(`#course_date_${lodgmentNum}`).find('.content_subject').eq(i).text();
            let addressArr = $(`#course_date_${lodgmentNum}`).find('.content_input').eq(i).val(); //eq() 는 찾은 값 중에 몇번째인지 선택할 때 사용
            // 좌표 검색
            geocoder.addressSearch(addressArr, function(result, status) {
                if (status === kakao.maps.services.Status.OK) {
                    let latlng = new kakao.maps.LatLng(result[0].y, result[0].x);
                    // pathList.push(latlng);
                    let newMarker = new kakao.maps.Marker({
                        map: map, // 마커를 표시할 지도
                        position: latlng // 마커를 표시할 위치
                    });
                    let newInfowindow = new kakao.maps.InfoWindow({
                        content: `<div style="width:150px;text-align:center;padding:6px 0;">${contentName}</div>`
                    });
                    
                    markers.push(newMarker);
                    infowindows.push(newInfowindow);
                    newInfowindow.open(map, newMarker);
                }
            });
        }
        // drawPath();
        map.setLevel(11); // 맵 확대 축소
        let mapCenter = new kakao.maps.LatLng(33.450701, 126.570667)
        map.setCenter(mapCenter);
    });
});


// 마커 지우는 함수
function deleteMarker() {
    marker.setMap(null);
    for(let marke of markers) {
        marke.setMap(null);
    }
}

// 인포윈도우 지우는 함수
function deleteInfoWindow() {
    infowindow.close();
    for(let info of infowindows) {
        info.close();
    }
}




/*
// div 태그 캡쳐해서 이미지파일 저장
function pringDiv(div) {
    div = div[0];
    html2canvas(div).then(function(canvas) {
        let myImage = canvas.toDataURL();
        const courseName = $('#course-name').val();
        console.log('courseName: ' + courseName);
        downloadURI(myImage, `${courseName}.png`);
    })
}

function downloadURI(uri, imageName) {
    let link = document.createElement('a');
    link.download = imageName;
    link.href = uri;
    document.body.appendChild(link);
    link.click();
}


$(document).ready(function() {
    console.log('실행1');
    $('.chk-box2').click(function() {
        pringDiv($('#map'));
    });
});
*/