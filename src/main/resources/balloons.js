function ready(callbackFunction){
  if(document.readyState != 'loading')
    callbackFunction(event)
  else
    document.addEventListener("DOMContentLoaded", callbackFunction)
}

ready(event => {

    const balloons = document.getElementsByClassName('balloons')[0];
    const spotlight = document.getElementsByClassName('spotlight')[0];
    const regex = /Christof/;

    const callback = function(mutationsList, observer) {
        for (const mutation of mutationsList) {
            if (mutation.type === 'childList') {
                let node = mutation.addedNodes[0];
                if (node && node.innerHTML.match(regex)) {
                    balloons.classList.remove('hidden');
                } else {
                    balloons.classList.add('hidden');
                }
            }
        }
    };

    const observer = new MutationObserver(callback);
    observer.observe(spotlight, { childList: true, subtree: true });
    // observer.disconnect();

})
