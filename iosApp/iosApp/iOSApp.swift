import SwiftUI
import SpaceNewsKit

@main
struct iOSApp: App {
    
    @UIApplicationDelegateAdaptor(AppDelegate.self)
    var appDelegate: AppDelegate
    
    init() {
        SpaceNewsApp.shared.initializeSharedFramework()
    }
    
    var body: some Scene {
        WindowGroup {
            RootNavigationView(rootNavHost: appDelegate.rootNavHost)
        }
    }
}
