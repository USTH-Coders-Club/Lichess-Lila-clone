package views.html
package account

import lila.api.Context
import lila.app.templating.Environment._
import lila.app.ui.ScalatagsTemplate._

import controllers.routes

object close {

  import trans.settings._

  def apply(u: lila.user.User, form: play.api.data.Form[_], managed: Boolean)(implicit ctx: Context) =
    account.layout(
      title = s"${u.username} - ${trans.closeAccount.txt()}",
      active = "close"
    ) {
      div(cls := "account box box-pad")(
        h1(dataIcon := "j", cls := "text")(trans.closeAccount()),
        if (managed)
          p("Your account is managed, and cannot be closed.")
        else
          postForm(cls := "form3", action := routes.Account.closeConfirm)(
            div(cls := "form-group")(trans.closeAccountExplanation()),
            div(cls := "form-group")(cantOpenSimilarAccount()),
            form3.passwordModified(form("passwd"), trans.password())(autofocus, autocomplete := "off"),
            form3.actions(
              frag(
                a(href := routes.User.show(u.username))(trans.changedMindDoNotCloseAccount()),
                form3.submit(
                  trans.closeAccount(),
                  icon = "j".some,
                  confirm = closingIsDefinitive.txt().some,
                  klass = "button-red"
                )
              )
            )
          )
      )
    }
}
