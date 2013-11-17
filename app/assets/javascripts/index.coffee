#############################################
# Functions to run on document ready
#############################################
$ ->
  $('#shoppingCart').height("50px")

  #############################################
  # Change the prices in the thumbnail to proper dollar values
  #############################################
  $('.thumbnail').each (index, element) ->
    $small = $(element).children('div').children('h4').children('small')
    value = +"#{$small.text()}"
    $small.text("$#{value.toFixed(2)}")

  #############################################
  # Trigger the modal that displays the 'add new item' form
  #############################################
  $(document).on 'click', '#addItem', (event) ->
    event.stopPropagation()
    event.preventDefault()
    $('#addItemModal').modal()

  #############################################
  # Add a new inventory item
  # Do a simple verification of the form data, and then post
  # it via an ajax request.  If successful, make a new thumbnail for
  # the item, else display the error in the modal so the user can
  # fix it
  #############################################
  $('#addItemForm').submit (event) ->
    event.preventDefault()
    data = new FormData($('#addItemForm')[0])
    data.append('image', $('#image')[0].files[0])
    data.append('name', $('#name').val())
    data.append('quantity', $('#quantity').val())
    data.append('price', $('#price').val())
    data.append('password', $('#password').val())
    everythingOK = true
    $('#addItemForm input').each (index, element) ->
      value = $(element).val()
      if not value
        everythingOK = false
        $(element).tooltip({
        title: 'Please fill in the field'
        placement: 'right'
        }).tooltip('show')
    if everythingOK
      $.ajax
        url         : '/add/an/item'
        data        : data
        type        : 'POST'
        contentType : false
        cache       : false
        processData : false
        error       : (jqXHR, textStatus, errorThrown) ->
          $('#addItemModal').append """
            <div class="modal-footer alert alert-error" style="display:none;">
              <h3>#{jqXHR.statusText}</h3><h4>#{jqXHR.responseText}</h4>
            </div>
                                    """
          $('#addItemModal .modal-footer').fadeIn('slow')
        success     : (data) ->
          newItem = """
            <li class="span3" id="itemThumbnail">
              <div class="thumbnail" data-name="#{data.name}" data-quantity="#{data.quantity}" data-price="#{data.price}">
                <img src="#{data.imgPath}" />
                <div style="text-align: center">
                  <h4>
                    #{data.name}
                    <small>
                      $#{data.price.toFixed(2)}
                    </small>
                  </h4>
                  <p>Quantity: #{data.quantity}</p>
                </div>
              </div>
            </li>
          """
          $('.thumbnails').append(newItem)
          $('#addItemForm')[0].reset()
          $('#addItemModal').modal('toggle')

#############################################
# When the user clicks on one of the thumbnails, grab the relevant information
# and make a div that gets placed in the shopping cart
#############################################
$(document).on 'click', '.thumbnail', (event) ->
  event.preventDefault()
  event.stopPropagation()
  $item = $(@)
  itemName = $item.data('name')
  itemPrice = +"#{$item.data('price')}"
  itemQuantity = $item.data('quantity')
  inCartAlready = false
  $('#checkoutBtn').removeClass('disabled')
  $(".cartEntry").each (index, element) =>
    if $(element).data('name') is itemName then inCartAlready = true
  itemEntry = """
              <div class='cartEntry' data-name='#{itemName}' data-price='#{itemPrice}' data-quantity='#{itemQuantity}'>
                <input type='number' class='numInCart pull-right' value='1' min='1' max='#{itemQuantity}' />
                <div class='pull-left itemSummary'>
                  <button class="close pull-left removeItem">&times;</button>
                  <h4>#{itemName}
                    <small>$#{itemPrice.toFixed(2)}</small>
                  </h4>
                </div>
              </div>
              """
  if not inCartAlready
    $("#shoppingCart").append itemEntry
    $entries = $(".cartEntry")
    itemHeight = $entries.height()
    $("#shoppingCart").height("#{(itemHeight * $entries.length) + itemHeight + 15}px")


#############################################
# Function to check whether the new quantity for an item in the cart is valid
#############################################
$(document).on 'change', '.numInCart', (event) ->
  newVal = +$(@).val()
  minVal = +$(@).attr('min')
  maxVal = +$(@).attr('max')
  validQuantity = maxVal >= newVal >= minVal
  if not validQuantity
    if newVal < minVal
      $(@).val(minVal)
    else if newVal > maxVal
      $(@).val(maxVal)
    else
      $(@).val(minVal)

#############################################
# When the user clicks on one of the thumbnails, grab the relevant information
# and make a div that gets placed in the shopping cart
#############################################
$(document).on 'click', '.removeItem', (event) ->
  event.preventDefault()
  event.stopPropagation()
  $(@).parents('.cartEntry').remove()
  if $('#shoppingCart .cartEntry').length is 0 then $('#checkoutBtn').addClass('disabled')

#############################################
# Handler for when the user wants to checkout.  First check whether there are items
# in the cart (i.e. button disabled) and then extract the information for each item.
# Finally, populate and render the prioritization modal.
#############################################
$(document).on 'click', '#checkoutBtn', (event) ->
  event.preventDefault()
  event.stopPropagation()
  if $(@).hasClass('disabled') then return
  $('#summaryModal').modal('toggle')
  totalPrice = 0
  $(".cartEntry").each (index, element) ->
    itemName = $(@).data('name')
    itemPrice = $(@).data('price')
    buying = $(@).children('.numInCart').val()
    totalPrice += buying * itemPrice
    if $("#summaryTable ##{itemName}").length is 0
      $("#summaryTable").append """
        <tr class='priorityRow' id='#{itemName}' data-name='#{itemName}' data-buying='#{buying}'>
          <td><strong>#{itemName}</strong></td>
          <td>#{itemPrice}</td>
          <!--<td class='priorityButtons'>
            <div class="btn-group" data-toggle="buttons-radio">
              <button type="button" class="btn btn-small btn-info">Low</button>
              <button type="button" class="btn btn-small btn-info active">Neutral</button>
              <button type="button" class="btn btn-small btn-info">High</button>
            </div>
          </td>-->
        </tr>
      """
    else
      $("#summaryTable ##{itemName}").data("buying", "#{buying}")
  if $('.modal-footer .subtotal').length isnt 0 then $('.modal-footer .subtotal').remove()
  $('.modal-footer .control-group').append "<span class='add-on subtotal'>Cart Total: $#{totalPrice.toFixed(2)}</span>"


#############################################
# Double check that the user entered the necessary information and then
# submit their information to the server for calculation
#############################################
$(document).on 'click', '#startShopping', (event) ->
  event.preventDefault()
  event.stopPropagation()
  $('#summaryModal').modal('toggle')
  $('#purchaseSummary').empty().append """
      <h1 style="text-align:center">Thank you for your purchase</h1>
  """
  if $('#purchaseSummary').is(':hidden')
    $('#purchaseSummary').slideDown()
  $('html, body').animate({
    scrollTop: $('#purchaseSummary').offset().top
  }, 1000)  
  
   
