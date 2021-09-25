const student = document.getElementById('student');
const teacher = document.getElementById('teacher');
///const first_name = document.getElementById('first_name');
//const last_name = document.getElementById('last_name');
const password = document.getElementById('password');
const close1 = document.getElementById('close1');
const close2 = document.getElementById('close2');
const close3 = document.getElementById('close3');
const modal = document.getElementById('modal');
const student_interface = document.getElementById('student_interface');
const submit = document.getElementById('submit');
var canShow = true;

// Show modal
document.body.addEventListener("click", function(event) {
  
  event.preventDefault();
  if(canShow == true){
  modal.classList.add('show-modal');
  canShow = false;
  }
} ,false);

// Hide modal
close1.addEventListener('click', function(event) {
   event.stopPropagation();
   modal.classList.remove('show-modal');
   canShow = true;
});

//show student log in interface

student.addEventListener("click", function(event) {
  
  event.preventDefault();
  event.stopPropagation();
  modal.classList.remove('show-modal');
  student_interface.classList.add('show-table');
} ,true);

 

close2.addEventListener('click', function(){
  student_interface.classList.remove('show-table');
  canShow = true;
});

//show teacher log in interface

teacher.addEventListener("click", function(event) {
  
  event.preventDefault();
  event.stopPropagation();
  modal.classList.remove('show-modal');
  teacher_interface.classList.add('show-table');
} ,true);

 

close3.addEventListener('click', function(){
  teacher_interface.classList.remove('show-table');
  canShow = true;
});

//go to game selection interface

submit.addEventListener('click', function(event) {
  window.location.href="gamemode.html";
  
},true);