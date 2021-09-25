const back = document.getElementById('back');

back.addEventListener('click', function(event) {
      window.history.back(-1);
    },true);