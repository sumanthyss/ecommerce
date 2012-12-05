$(document).on 'click', '#testLink', () ->
  url = "/shoppinglist"
  myJSObject =
    budget: 4.22
    items  : [
      {name: 'apple', priority: 1}
      {name: 'banana', priority: 2}
    ]
  $.ajax url,
    data : JSON.stringify(myJSObject)
    contentType : 'application/json'
    type : 'POST'
    success: (data) ->
      console.log data