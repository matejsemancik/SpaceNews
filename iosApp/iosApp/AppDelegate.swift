import SwiftUI
import SpaceNewsKit

class AppDelegate: NSObject, UIApplicationDelegate {
    
    let rootNavHost: RootNavHost = RootNavHostFactory.shared.create(
        componentContext: defaultAppComponentContext(context: DefaultComponentContext(lifecycle: ApplicationLifecycle()))
    )
}
