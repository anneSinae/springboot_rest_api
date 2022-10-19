$(document).ready(function() {
	//downloadExcel();
});

/* json to excel */
function downloadExcel() {
	// making ArrayBuffer
    function s2ab(s) {
        var buf = new ArrayBuffer(s.length); //convert s to arrayBuffer
        var view = new Uint8Array(buf);  //create uint8array as viewer
        for (var i=0; i<s.length; i++) view[i] = s.charCodeAt(i) & 0xFF; //convert to octet
        return buf;
    }

    // create workbook
    var wb = XLSX.utils.book_new();

    // setting document property (엑셀파일 오른쪽클릭 속성 -> 자세히) no required
    wb.Props = {
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

    // create sheet name
    wb.SheetNames.push("sheet 1");
    // wb.SheetNames.push("sheet 2"); //for multiple sheet

    // inject data : double array(must make json to double array)
    var wsData = [['A1' , 'A2', 'A3'],['B1','B2','B3'],['C1','C2']];
	// var wsData2 = [['가1' , '가2', '가3'],['나1','나2','나3']]; //for multiple sheet

    // create sheed data with array data
    var ws = XLSX.utils.aoa_to_sheet(wsData);
	// var ws2 = XLSX.utils.aoa_to_sheet(wsData2); //for multiple sheet
    
    // inject sheet data into sheet (unnamed sheet data is injected on first sheet)
    wb.Sheets["sheet 1"] = ws;
    // wb.Sheets["sheet 2"] = ws2; //for multiple sheet

    // write excel file
    var wbout = XLSX.write(wb, {bookType:'xlsx',  type: 'binary'});

    // download file
    saveAs(new Blob([s2ab(wbout)],{type:"application/octet-stream"}), 'excel_sinae.xlsx');
}