document.getElementById("answer").style.display = "none";
document.getElementById("myanswer").style.display = "none";
var quest = document.getElementById("question");
var myanswer = document.getElementById("myanswer");
var button = document.getElementById("button-search");

button.addEventListener('keyup', function (event) {
    if (event.key === 'Enter') {
        var inputValue = quest.value;
        var answer = myanswer.value;
        var newQ = {
            question: inputValue,
            answer: answer
        };
        axios.post('question', newQ)
            .then(function (result) {
                document.getElementById("myanswer").style.display = "none";
                myanswer.value="";
                quest.value="";
            })
            .catch(function (reason) {
                console.error(reason);
            });
    }
});

myanswer.addEventListener('keyup', function (event) {
    if (event.key === 'Enter') {
        var inputValue = quest.value;
        var answer = myanswer.value;
        var newQ = {
            question: inputValue,
            answer: answer
        };
        axios.post('question', newQ)
            .then(function (result) {
                document.getElementById("myanswer").style.display = "none";
                myanswer.value="";
                quest.value="";
            })
            .catch(function (reason) {
                console.error(reason);
            });
    }
});

question.addEventListener('keyup', function (event) {
    if (event.key === 'Enter') {
        axios.get('question', {
            params: {
                answer:quest.value
            }
        })
            .then(function (result) { //if request was successfully served, we get back the data
                var ans = result.data.answer;
                document.getElementById("answer").style.display = "block";
                document.getElementById("answer").value = ans;



            })
            .catch(function (reason) {
                if (reason.response.status==500){
                    document.getElementById("answer").style.display = "none";
                    myanswer.value="";
                    document.getElementById("myanswer").style.display = "block";
                }

            });

    }
});