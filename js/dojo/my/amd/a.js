var isGlobalVar = "Hi, there~";
function getGreetings(someone) {
  return "Hi, " + someone + "~";
}
define( {
  a : "AAA",
  say: getGreetings
});
