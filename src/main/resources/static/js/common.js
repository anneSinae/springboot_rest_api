$(document).ready(function() {
	//downloadExcel();
});

/* file validation */
function validateImg(inputId, option){
	var target = document.getElementById(inputId);
	var targetList = target.files;
	var fileType = setFileTypeReg(option.fileType || "doc");
	var required = option.required || true;
	var maxSizeMB = option.maxSizeMB || 5;
	var maxSize = maxSizeMB * 1024 * 1024;
	var fileSize;
	var typeGuide;
	
	console.log(targetList.length);
	if(required && !targetList.length) {
		alert("첨부파일은 필수입니다.");
	    $("#" + inputId).focus();
	    return false;
	}
	
	for(idx = 0; idx < targetList.length; idx++) {
	    fileSize = targetList[idx].size;
	    if(!targetList[idx].name.match(fileType)) {
			alert(typeGuide + "파일만 업로드 가능합니다.");
	        return;
	    } 
	    if(fileSize > maxSize) {
	    	alert("파일별 사이즈는 " + maxSizeMB + "MB까지 가능합니다.");
	        return;
	    }
	};
	
	function setFileTypeReg(type) {
		var typeReg;
		switch(type) {
			case "img" : typeReg = /(.*?)\.(jpg|jpeg|png|gif|bmp|pdf|svg)$/; typeGuide = "이미지"; break;
			case "doc" : typeReg = /(.*?)\.(doc|docx|html|pdf|rtf|pdf|xml|csv|txt|xlsx|xls|pptx|ppt|hwp|hwpx)$/; typeGuide = "문서"; break;
			default : typeReg = /(.*?)\.(jpg|jpeg|png|gif|bmp|pdf|svg|doc|docx|html|pdf|rtf|pdf|xml|csv|txt|xlsx|xls|pptx|ppt|hwp|hwpx)$/;
		}
		return typeReg;
	}
	return true;
}


/* json to excel */
function downloadExcel() {
    function arrBuffer(s) { // create ArrayBuffer
        var buf = new ArrayBuffer(s.length); //convert s to arrayBuffer
        var view = new Uint8Array(buf);  //create uint8array as viewer
        for (var i=0; i<s.length; i++) view[i] = s.charCodeAt(i) & 0xFF;
        return buf;
    }

    var wb = XLSX.utils.book_new(); // create workbook
    wb.Props = { // setting document property(엑셀파일 오른쪽클릭 속성 -> 자세히) no required
        Title: "title",
        Subject: "subject",
        Author: "sinae",
        Manager: "Manager",
        Company: "Company",
        Category: "Category",
        Keywords: "Keywords",
        Comments: "Comments",
        LastAuthor: "sinae",
        CreatedDate: new Date(2021,01,13)
    };

    wb.SheetNames.push("sheet 1");
    // wb.SheetNames.push("sheet 2"); // multi-sheet

    // double array(must make json to double array)
    var wsData = [['A1' , 'A2', 'A3'],['B1','B2','B3'],['C1','C2']]; 
	// var wsData2 = [['가1' , '가2', '가3'],['나1','나2','나3']]; // multi-sheet

    var ws = XLSX.utils.aoa_to_sheet(wsData);
	// var ws2 = XLSX.utils.aoa_to_sheet(wsData2); // multi-sheet
    
    wb.Sheets["sheet 1"] = ws; // data into sheet (unnamed sheet data is injected on first sheet)
    // wb.Sheets["sheet 2"] = ws2; // multi-sheet

    var wbout = XLSX.write(wb, {bookType:'xlsx',  type: 'binary'});
    saveAs(new Blob([arrBuffer(wbout)],{type:"application/octet-stream"}), 'excel_sinae.xlsx');
}