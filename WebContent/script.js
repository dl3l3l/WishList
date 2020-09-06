function win_open_big(folder,page) {
	var op = "width=600, height=450, left=50, top=150";
	open("../"+folder+"/"+page,"",op);
}

function win_open_small(folder,page) {
	var op = "width=500, height=150, left=150, top=150";
	open("../"+folder+"/"+page,"",op);
}

function win_open_find(folder,page) {
	var op = "width=400, height=200, left=50, top=50";
	open("../member/"+page+".do","",op);
}

function win_upload() {
	var op = "width=500, height=150, left=50, top=150";
	open("pictureForm.do", "", op);
}

function closer() {
	self.close();
}