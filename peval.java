<button onclick="myOrganizer()">Organize Contact</button>

<p id="demo"></p>

<script>
function myOrganizer() {
	var type=0;
    var strCreate = prompt("Please enter your string", "Doug Jones");
	var result=" ";
	for(var i=0;i<=strCreate.length;i++)
	{
		var letterNumber = /^[0-9a-zA-Z]+$/;
		if((strCreate[i].value.match(letterNumber) and strCreate[i+1].value.match(" ") and strCreate[i].value.match(letterNumber)))
		{
			type=1;
		}
	}
}
</script>