#//developed by coffee script
jQuery.fn.grid = () ->
  $this = $ this
  $this.data('grid', new Grid(this))

class Grid

  constructor: (container)->
    @container = $ container
    this._register_a_events()
    this._register_events()
    this._prepare_submit_form()
    this._update_action_state()

  _register_a_events: ->
    wice = this

    @container.find('.grid-action').bind( 'click', (event)->
      $this = $ this

      if($this.closest("li").hasClass("disabled"))
        event.preventDefault()
        return false

      if($this.attr("confirm") && !confirm($this.attr("confirm")))
        event.preventDefault()
        return false

      ids = wice._get_checked_record_ids()

      if ($this.attr("href").indexOf("keys") >= 0 and ids.length > 0) or $this.attr("href").indexOf("keys") < 0
        #replace `:key` to real ids, eg: /post/keys/edit => /post/1/edit
        url = $this.attr("href").replace /keys/, ids.join(',')
        $this.attr("href", url)

        if($this.attr("method"))
          wice._submit_to url, $this.attr("method")
          event.preventDefault()
      else
        event.preventDefault()
    )

    @container.find('.table-wrapper a').bind('click', (event) ->
      wice._update_with($(this).attr("href"))
      event.preventDefault()
    )


  _register_events: ->
    wice = this

    @container.find('form').submit (event)->
      wice._update_with($(this).attr("action"))
      event.preventDefault();

    @container.delegate('.all-checker', 'click', ->
      wice.container.find('.row-checker').each(->
        this.checked = $('.all-checker').get(0).checked
        return true
      )

      wice._update_action_state()
    )

    @container.delegate('.row-checker', 'click', ->
      wice._update_action_state()
    )

  _update_with: (url) ->
    wice = this
    $.get(url, (data)->
      wice.container.find('.table-wrapper a').unbind('click')
      wice.container.find('.grid-action').unbind('click')
      wice.container.find('.table-wrapper').replaceWith(data)
      wice._register_a_events()
    )

  _submit_to: (url, method)->
    @wice_form.attr("action", url)
    @wice_form.attr("method", "post")
    @wice_method.val(method)
    @wice_form.get(0).submit()

  _update_action_state: ->
    checked_size = @container.find('.row-checker:checked').size()
    @container.find(".grid-action").each(->
      mode = $(this).attr("mode")
      disabled = false
      switch mode
        when "0"
          disabled = checked_size != 0
        when "1"
          disabled = checked_size != 1
        when "2"
          disabled = checked_size != 2
        when "+"
          disabled = checked_size == 0
        else
          disabled = false
      if(disabled)
        $(this).closest('li').addClass("disabled")
      else
        $(this).closest('li').removeClass("disabled")
    )

  _get_checked_record_ids: ->
    ids = []
    @container.find(".row-checker:checked").each ->
      ids.push($(this).attr("key"))
    ids

  _prepare_submit_form: ->
    $(document.body).append("<form id='wice_form' method='get'><input type='hidden' id='wice_method' name='x-http-method-override' /></form>")
    @wice_form = $("#wice_form")
    @wice_method = $("#wice_method")


